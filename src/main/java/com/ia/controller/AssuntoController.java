package com.ia.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ia.model.Assunto;
import com.ia.service.AssuntoService;

@RestController
public class AssuntoController {

	@Autowired
	AssuntoService assuntoService;
	
	
	@RequestMapping(method=RequestMethod.POST, value="/assunto/salvar", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Assunto>salvar(@RequestBody Assunto assunto) {
		Assunto alterado = assuntoService.salvar(assunto);
		return new ResponseEntity<Assunto>(alterado, HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/assunto/buscartodos", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Assunto>> buscarTodos() {
		
		return new ResponseEntity<>(assuntoService.buscarTodos(), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/assunto/buscarporid/{id}")
	public ResponseEntity<Assunto> buscarPorId(@PathVariable("id") Integer id) {
		
		return new ResponseEntity<>(assuntoService.buscarPorId(id), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.GET, value="/assunto/pesquisar/{pageSize}&{currentPage}&{texto}")
	public ResponseEntity<Collection<Assunto>> buscarPorDemanda(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage, @PathVariable("texto") String texto) {
		return new ResponseEntity<>(assuntoService.buscarPorDemanda(texto, pageSize, currentPage), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/assunto/pesquisarquantidade/{texto}")
	public ResponseEntity<Integer> buscarQuantidadeRegistro(@PathVariable("texto") String texto) {
		if(texto.equals("nulo")) texto = "";
		return new ResponseEntity<Integer>(assuntoService.buscarQuantidadeRegistro(texto), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/assunto/excluir/{id}")
	public ResponseEntity<String> excluir(@PathVariable Integer id) {
		
		Assunto assunto = assuntoService.buscarPorId(id);
		assuntoService.excluir(assunto);
		if(assunto == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else
			return new ResponseEntity<>(HttpStatus.OK);

	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/assunto/reativar/{id}")
	public ResponseEntity<String> reativar(@PathVariable Integer id) {
		
		Assunto assunto = assuntoService.buscarPorId(id);
		assuntoService.reativar(assunto);
		if(assunto == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else
			return new ResponseEntity<>(HttpStatus.OK);

	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/assunto/alterar", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Assunto> alterar(@RequestBody Assunto assunto) {
		
		return new ResponseEntity<>(assuntoService.salvar(assunto), HttpStatus.OK);
	}
}
