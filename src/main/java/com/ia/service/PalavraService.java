package com.ia.service;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ia.model.Palavra;
import com.ia.repository.PalavraRepository;

@Service
public class PalavraService {

	@Autowired
	PalavraRepository palavraRepository;
	
	
	
	public Palavra salvar(Palavra palavra) {

		palavraRepository.save(palavra);
		return palavra;
	}
	
	public Collection<Palavra> buscarTodos() {
		return  palavraRepository.findAll();
	}
	
	public void excluir(Palavra palavra) {
		palavraRepository.delete(palavra);
	}
	
	public Palavra buscarPorId(Integer id) {
		return palavraRepository.findOne(id); 
	}
	
	public Palavra findByPalavra(String palavra){
		return palavraRepository.findByPalavra(palavra);
	}
	
	
	

}
