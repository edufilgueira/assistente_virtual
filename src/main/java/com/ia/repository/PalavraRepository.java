package com.ia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ia.model.Palavra;

@Repository
public interface PalavraRepository extends JpaRepository<Palavra, Integer>{
	
	Palavra findByPalavra(String palavra);
	

}
