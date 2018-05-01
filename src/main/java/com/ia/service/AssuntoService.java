package com.ia.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ia.model.Assunto;
import com.ia.repository.AssuntoRepository;

@Service
public class AssuntoService {
	
	@Autowired
	AssuntoRepository assuntoRepository;

	public Assunto salvar(Assunto assunto) {

		assuntoRepository.save(assunto);
		return assunto;
	}
	
	public Collection<Assunto> buscarTodos() {
		return  assuntoRepository.findByAllOrderByIdAsc();
	}
	
	public Collection<Assunto> buscarPorDemanda(String texto, Integer pageSize, Integer currentPage) {
		return  assuntoRepository.buscarPorDemanda(texto, pageSize, currentPage);
	}
	
	public Integer buscarQuantidadeRegistro(String texto) {
		return  assuntoRepository.buscarQuantidadeRegistro(texto);
	}
	
	public void excluir(Assunto assunto) {
		assunto.setId(assunto.getId());
		assunto.setStatus(0);
		assuntoRepository.save(assunto);
	}
	
	public void reativar(Assunto assunto) {
		assunto.setId(assunto.getId());
		assunto.setStatus(1);
		assuntoRepository.save(assunto);
	}
	
	public Assunto buscarPorId(Integer id) {
		return assuntoRepository.findOne(id); 
	}

}
