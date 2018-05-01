package com.ia.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ia.model.Assunto;
import com.ia.model.Interacao;
import com.ia.model.Palavra;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

@Service
public class NeuralService {
	
	@Autowired
	AssuntoService assuntoService;

	@Autowired
	InteracaoService interacaoService;
	
	@Autowired
	PalavraService palavraService;
	
	private Instances instancias;
	
	public String submeterParaTesteDeDados(String texto) throws Exception
	{
		
		String lista = this.decodificarTexto(texto);	
		String[] cod = lista.split(",");
		
		carregaBaseArff();
		
		ObjectInputStream modeloRedeNeural = new ObjectInputStream(new FileInputStream("MapaNeural.model"));
		MultilayerPerceptron baseTreinada = (MultilayerPerceptron) modeloRedeNeural.readObject();
        modeloRedeNeural.close();

        Instance novo = new DenseInstance(instancias.numAttributes());
        novo.setDataset(instancias);
        for (int i=0; i < cod.length; i++) {
			novo.setValue(i, Integer.parseInt(cod[i]) );
		}
        
        double resultado[] = baseTreinada.distributionForInstance(novo);
        DecimalFormat df = new DecimalFormat("#,###0.0");
        int index = retornarPosicaoResultadoRedeNeural(resultado);
        String[] arrayClasses = retornarClasseResultadoRedeNeural();
        
		return "Resultado:\n "+arrayClasses[index]+" ("+df.format(resultado[index]*100)+"%)";
	}
	
	private String[] retornarClasseResultadoRedeNeural() throws IOException {
		FileReader arquivo = new FileReader(new File("MapaNeural.arff"));
		BufferedReader ler_arquivo = new BufferedReader(arquivo);
		String linha = ler_arquivo.readLine();
		String classes = "";
		while(linha != null) {
			if(linha.contains("@attribute class")) {
				classes = linha.substring(18, linha.length()-1);
				break;
			}
			linha = ler_arquivo.readLine();
		}
		ler_arquivo.close();
		String[] arrayClasses = classes.split(",");
		return arrayClasses;
	}

	private Integer retornarPosicaoResultadoRedeNeural(double[] resultado) {
        double maiorValor = resultado[0];
        int index = 0;
        
        for(int i = 0; i < resultado.length; i++)
        {
        	if(maiorValor < resultado[i]) {
        		maiorValor = resultado[i];
        		index = i;
        	}
        }
        return index;
	}

	private Collection<Assunto> listarRetornosAssunto() {
		 return assuntoService.buscarTodos();
	}
	
	private Collection<Interacao> listarTextosInteracao() {
		 return interacaoService.buscarTodos();
	}
	
	private boolean verificarPalavras(String palavra) {
		Palavra objPalavra = palavraService.findByPalavra(Funcoes.conversaoFonetica(palavra));
		if(objPalavra != null)
			return true;
		else
			return false;
	}
	
	public void salvarPalavras() throws Exception {
		Collection<Interacao> interacoes = interacaoService.buscarTodos();
		for(Interacao interacao : interacoes){	
			String[] palavras = interacao.getTexto().split(" ");
			for(String palavra : palavras) 
			{
				palavra = palavra.trim();
				if(!verificarPalavras(palavra))
					salvarPalavra(palavra);
			}
		}
		gerarArquivoArff();
	}
	
	private void salvarPalavra(String palavra) {
		Palavra _palavra = new Palavra();
		_palavra.setPalavra(Funcoes.conversaoFonetica(palavra));
		_palavra.setOriginal(palavra);
		palavraService.salvar(_palavra);
	}

	public String decodificarTexto(String texto) {
		String[] palavraTexto = texto.split(" ");
		String textoCodificado = "";
		
		for(Palavra objPalavra : palavraService.buscarTodos()) 
		{
			boolean existePalavra = false;
			for(String palavra : palavraTexto) 
			{
				palavra = palavra.trim();
				palavra = Funcoes.conversaoFonetica(palavra);
				if( objPalavra.getPalavra().equals(palavra))
					existePalavra = true;
			}
			
			if(existePalavra)
				textoCodificado += "1,";
			else
				textoCodificado += "0,";
		}
		
		return textoCodificado.substring(0,textoCodificado.length()-1);
	}
	
	private void gerarArquivoArff() {
		FileWriter arquivo;
		try {
			
			Collection<Palavra> palavras = palavraService.buscarTodos();
			arquivo = new FileWriter(new File("MapaNeural.arff"));
			BufferedWriter novaLinha = new BufferedWriter(arquivo);
			novaLinha.write("@relation MapaNeural");
			novaLinha.newLine();
			
			for(Palavra pa : palavras){
				novaLinha.newLine();
				novaLinha.write("@attribute "+pa.getPalavra().toString()+" numeric");	
			}
			
			String classe = "@attribute class {";
			for(Assunto assunto : this.listarRetornosAssunto()) {
				classe += assunto.getRetorno()+",";
			}
			
			classe = classe.substring(0, classe.length()-1)+"}";
			novaLinha.newLine();
			novaLinha.write(classe);
			novaLinha.newLine();
			novaLinha.write("@data");
			
			for(Interacao interacao : this.listarTextosInteracao()) {
				String textoDecodificado = decodificarTexto(interacao.getTexto());
				novaLinha.newLine();
				novaLinha.write(textoDecodificado+","+interacao.getAssunto().getRetorno());
			}
			novaLinha.close();
			trinarRedeNeural();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void trinarRedeNeural() throws Exception {
		carregaBaseArff();
        MultilayerPerceptron multilayerPerceptron = new MultilayerPerceptron();
        multilayerPerceptron.setHiddenLayers("a");
        multilayerPerceptron.setLearningRate(0.1);
        multilayerPerceptron.setMomentum(0.1);
        multilayerPerceptron.setTrainingTime(1000);
        multilayerPerceptron.buildClassifier(instancias);

        ObjectOutputStream classificador = new ObjectOutputStream(new FileOutputStream("MapaNeural.model"));
        classificador.writeObject(multilayerPerceptron);
        classificador.flush();
        classificador.close();
	}
	
	public void carregaBaseArff() throws Exception {
        DataSource ds = new DataSource("MapaNeural.arff");
        instancias = ds.getDataSet();
        instancias.setClassIndex(instancias.numAttributes() - 1);
    }

}
