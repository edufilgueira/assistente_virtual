package com.ia.controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;
import ptstemmer.Stemmer;


/**
 * NER Training in OpenNLP with Name Finder Training Java Example
 * @author www.tutorialkart.com
 */
@RestController
public class NERTrainingExample {
 
	@RequestMapping(method=RequestMethod.GET, value="/NERTrainingExample")
    public void NERTrainingExample() {
 
        // reading training data
        InputStreamFactory in = null;
        try {
            in = new MarkableFileInputStreamFactory(new File("Teste/NomePessoas.txt"));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        
        ObjectStream sampleStream = null;
        try {
            sampleStream = new NameSampleDataStream(
                new PlainTextByLineStream(in, StandardCharsets.UTF_8));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
 
        // setting the parameters for training
        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, 70);
        params.put(TrainingParameters.CUTOFF_PARAM, 1);
 
        // training the model using TokenNameFinderModel class 
        TokenNameFinderModel nameFinderModel = null;
        try {
            nameFinderModel = NameFinderME.train("en", null, sampleStream, params, TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // saving the model to "ner-custom-model.bin" file
        try {
            File output = new File("Teste/pt-pessoas-model.bin");
            FileOutputStream outputStream = new FileOutputStream(output);
            nameFinderModel.serialize(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // testing the model and printing the types it found in the input sentence
        TokenNameFinder nameFinder = new NameFinderME(nameFinderModel);
        
        String[] testSentence ={"Alisa","Fernandes","is","a","tourist","from","Spain"};
 
        System.out.println("Finding types in the test sentence..");
        Span[] names = nameFinder.find(testSentence);
        for(Span name:names){
            String personName="";
            for(int i=name.getStart();i<name.getEnd();i++){
                personName+=testSentence[i]+" ";
            }
            System.out.println(name.getType()+" : "+personName+"\t [probability="+name.getProb()+"]");
        }
    }
	@RequestMapping(method=RequestMethod.GET, value="/stemmer/{palavra}")
    public String stemmer(@PathVariable String palavra) throws Exception {
		Stemmer st = Stemmer.StemmerFactory(Stemmer.StemmerType.ORENGO);
		st.enableCaching(1000);
		st.ignore("a","e");
		
		String[] listaPalavras = palavra.split(" ");
		String textoFinal = "";
		for(String listaPalavra : listaPalavras ) {
			listaPalavra = listaPalavra.trim();
			textoFinal += " "+st.getWordStem(listaPalavra);
		}
		return textoFinal;
	}

 
}