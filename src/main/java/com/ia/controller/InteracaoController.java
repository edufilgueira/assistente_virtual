package com.ia.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ia.model.Interacao;
import com.ia.service.InteracaoService;
import com.ia.service.NeuralService;

@RestController
public class InteracaoController {

	@Autowired
	InteracaoService interacaoService;
	
	@Autowired
	NeuralService neuralService;
	
	
	@RequestMapping(method=RequestMethod.POST, value="/interacaoapi", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Interacao>salvar(@RequestBody Interacao interacao) {

		Interacao alterado = interacaoService.salvar(interacao);
		try {
			neuralService.salvarPalavras();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Interacao>(alterado, HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/interacaoapi", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Interacao>> buscarTodos() {
		
		return new ResponseEntity<>(interacaoService.buscarTodos(), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/interacaoapi/{id}")
	public ResponseEntity<String> excluir(@PathVariable Integer id) {
		
		Interacao interacao = interacaoService.buscarPorId(id);
		interacaoService.excluir(interacao);
		if(interacao == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(HttpStatus.OK);

	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/interacaoapi", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Interacao> alterar(@RequestBody Interacao interacao) {
		
		return new ResponseEntity<>(interacaoService.salvar(interacao), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/interacao/buscarPorAssunto/{assuntoId}")
	public List<Interacao> buscarPorAssunto(@PathVariable Integer assuntoId) {
		
		return interacaoService.buscarPorAssunto(assuntoId);
	}
}
