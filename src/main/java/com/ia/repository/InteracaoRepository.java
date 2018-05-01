package com.ia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ia.model.Interacao;

@Repository
public interface InteracaoRepository extends JpaRepository<Interacao, Integer>{
	
	//@Query("select c from interacao c where c.assunto_id = ?1")
	@Query( value="SELECT * FROM interacao WHERE assunto_id = ?1", nativeQuery = true)
	List<Interacao> findByAssunto(Integer assuntoId);

}
