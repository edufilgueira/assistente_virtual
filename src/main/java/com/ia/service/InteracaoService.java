package com.ia.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ia.model.Interacao;
import com.ia.repository.InteracaoRepository;

@Service
public class InteracaoService {

	@Autowired
	InteracaoRepository interacaoRepository;

	public Interacao salvar(Interacao interacao) {

		interacaoRepository.save(interacao);
		return interacao;
	}
	
	public Collection<Interacao> buscarTodos() {
		return  interacaoRepository.findAll();
	}
	
	public void excluir(Interacao interacao) {
		interacaoRepository.delete(interacao);
	}
	
	public Interacao buscarPorId(Integer id) {
		return interacaoRepository.findOne(id); 
	}
	
	public List<Interacao> buscarPorAssunto(Integer id) {
		return interacaoRepository.findByAssunto(id); 
	}
}
