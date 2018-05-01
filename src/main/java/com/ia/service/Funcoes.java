package com.ia.service;

import java.text.Normalizer;

import org.springframework.stereotype.Service;

import ptstemmer.Stemmer;
import ptstemmer.exceptions.PTStemmerException;

@Service
public abstract class Funcoes {

	public static String removerAcentos(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	public static String conversaoFonetica(String palavra) {
		Stemmer st;
		String retorno = "";
		try {
			st = Stemmer.StemmerFactory(Stemmer.StemmerType.ORENGO);
			st.enableCaching(1000);
			st.ignore("a","e");
			retorno = st.getWordStem(palavra);
			retorno = removerAcentos(retorno);
		} catch (PTStemmerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return retorno;
	}
}
