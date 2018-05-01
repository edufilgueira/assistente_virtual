package com.ia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ia.service.NeuralService;

@RestController
public class NeuralController {
	
	@Autowired
	private NeuralService neuralService;
	
	/*@RequestMapping(method=RequestMethod.POST, value="/robo", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String getResposta(@RequestBody String texto) throws Exception {
		//return neuralService.testarDados(texto);
		return "o que pé issso";
	}*/ 
	
	@RequestMapping(method=RequestMethod.GET, value="/robo/{texto}")
	public String getResposta(@PathVariable("texto") String texto) throws Exception {
		return neuralService.submeterParaTesteDeDados(texto);
	}
}
