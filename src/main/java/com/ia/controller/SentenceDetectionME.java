package com.ia.controller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;  


@RestController
public class SentenceDetectionME { 
  
	private String sent = "Ola. Como você vai? Primeiramente quero desejar uma excelente semana. " + 
      		"Nós preparamos um tutorial excelente para você. Deseja conhecer mais?";
	
	@RequestMapping(method=RequestMethod.GET, value="/sentenceDetectionME")
	public void sentenceDetectionME() throws Exception {

      String sentence = "Ola. Como você vai? Primeiramente quero desejar uma excelente semana. " 
         + "Nós preparamos um tutorial! excelente para você"; 
       
      //Loading sentence detector model 
      InputStream inputStream = new FileInputStream("OpenNLP_models/en-sent.bin"); 
      SentenceModel model = new SentenceModel(inputStream); 
       
      //Instantiating the SentenceDetectorME class 
      SentenceDetectorME detector = new SentenceDetectorME(model);  
    
      //Detecting the sentence
      String sentences[] = detector.sentDetect(sentence); 
      //Printing the sentences 
      for(String sent : sentences)        
         System.out.println(sent);
      
      //Detecting the position of the sentences in the paragraph  
      Span[] spans = detector.sentPosDetect(sentence); 
      //Printing the sentences and their spans of a sentence 
      for (Span span : spans)      
    	  System.out.println(sentence.substring(span.getStart(), span.getEnd())+" "+ span);
   } 
	
	@RequestMapping(method=RequestMethod.GET, value="/SentenceDetectionMEProbs")
	public void SentenceDetectionMEProbs() throws Exception { 
		   
	      String sentence = "Ola. Como você vai? Primeiramente quero desejar uma excelente semana. " + 
	      		"Nós preparamos um tutorial excelente para você. Deseja conhecer mais?"; 
	       
	      //Loading sentence detector model 
	      InputStream inputStream = new FileInputStream("OpenNLP_models/en-sent.bin");
	      SentenceModel model = new SentenceModel(inputStream); 
	       
	      //Instantiating the SentenceDetectorME class
	      SentenceDetectorME detector = new SentenceDetectorME(model);  
	      
	      //Detecting the sentence 
	      String sentences[] = detector.sentDetect(sentence); 
	    
	      //Printing the sentences 
	      for(String sent : sentences)        
	         System.out.println(sent);   
	         
	      //Getting the probabilities of the last decoded sequence       
	      double[] probs = detector.getSentenceProbabilities(); 
	       
	      System.out.println("  "); 
	       
	      for(int i = 0; i<probs.length; i++) 
	         System.out.println(probs[i]); 
	   } 
	
	   @RequestMapping(method=RequestMethod.GET, value="/SimpleTokenizerSpans")
	   public void SimpleTokenizerSpans(){ 
		   
		      //Instantiating SimpleTokenizer class 
		      SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;  
		       
		      //Retrieving the boundaries of the tokens 
		      Span[] tokens = simpleTokenizer.tokenizePos(sent);  
		       
		      //Printing the spans of tokens 
		      for( Span token : tokens)
		         System.out.println(token +" "+sent.substring(token.getStart(), token.getEnd()));          
		   } 
	   
	   @RequestMapping(method=RequestMethod.GET, value="/WhitespaceTokenizerSpans")
	   public void WhitespaceTokenizerSpans(){ 
		    
		      //Instantiating SimpleTokenizer class 
		      WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE;  
		       
		      //Retrieving the tokens 
		      Span[] tokens = whitespaceTokenizer.tokenizePos(sent);  
		       
		      //Printing the spans of tokens 
		      for( Span token : tokens) 
		         System.out.println(token + " "+ sent.substring(token.getStart(), token.getEnd()));        
		   
	   } 
	   
	   @RequestMapping(method=RequestMethod.GET, value="/TokenizerMESpans")
	   public void TokenizerMESpans () throws IOException{ 
		   
	       
		      //Loading the Tokenizer model 
		      InputStream inputStream = new FileInputStream("OpenNLP_models/en-token.bin"); 
		      TokenizerModel tokenModel = new TokenizerModel(inputStream); 
		       
		      //Instantiating the TokenizerME class 
		      TokenizerME tokenizer = new TokenizerME(tokenModel); 
		       
		      //Retrieving the positions of the tokens 
		      Span tokens[] = tokenizer.tokenizePos(sent); 
		       
		      //Printing the spans of tokens 
		      for(Span token : tokens) 
		         System.out.println(token +" "+sent.substring(token.getStart(), token.getEnd()));      

	   
	   
	   }
	   
	   
	   @RequestMapping(method=RequestMethod.GET, value="/TokenizerMEProbs")
	   public void TokenizerMEProbs() throws IOException{ 
		   
		     //Loading the Tokenizer model 
		      InputStream inputStream = new FileInputStream("OpenNLP_models/pt-token.bin"); 
		      TokenizerModel tokenModel = new TokenizerModel(inputStream); 
		      
		      //Instantiating the TokenizerME class 
		      TokenizerME tokenizer = new TokenizerME(tokenModel);
		      
		      //Retrieving the positions of the tokens 
		      Span tokens[] = tokenizer.tokenizePos(sent); 
		       
		      //Getting the probabilities of the recent calls to tokenizePos() method 
		      double[] probs = tokenizer.getTokenProbabilities(); 
		       
		      //Printing the spans of tokens 
		      for(Span token : tokens) 
		         System.out.println(token +" "+sent.substring(token.getStart(), token.getEnd()));      
		         System.out.println("  "); 
		         for(int i = 0; i<probs.length; i++) 
		            System.out.println(probs[i]);          
	   
	   }
	   
	   
	   @RequestMapping(method=RequestMethod.GET, value="/NameFinderME_Example")
	   public void NameFinderME_Example() throws Exception{ 
		      //Loading the NER - Person model       
		   InputStream inputStream = new FileInputStream("OpenNLP_models/en-ner-person.bin"); 
		      TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
		      
		      //Instantiating the NameFinder class 
		      NameFinderME nameFinder = new NameFinderME(model); 
		    
		      //Getting the sentence in the form of String array  
		      String [] sentence = new String[]{ 
		         "Mike", 
		         "and", 
		         "Smith", 
		         "are", 
		         "good",
		         "marcus", 
		         "eduardo", 
		         "friends" 
		      }; 
		       
		      //Finding the names in the sentence 
		      Span nameSpans[] = nameFinder.find(sentence); 
		       
		      //Printing the spans of the names in the sentence 
		      for(Span s: nameSpans) 
		         System.out.println(s.toString());    
		   } 
	
}