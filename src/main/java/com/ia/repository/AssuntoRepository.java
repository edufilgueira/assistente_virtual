package com.ia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ia.model.Assunto;


@Repository
public interface AssuntoRepository extends JpaRepository<Assunto, Integer>{
		
	@Query( value="SELECT * FROM assunto order by id", nativeQuery = true)
	List<Assunto> findByAllOrderByIdAsc();
	
	@Query( value="Select * from assunto "
				+ "where (retorno) ILIKE LOWER(CONCAT('%', :texto, '%')) "
				+ "order by id asc "
				+ "Offset :pageSize*(:currentPage-1) fetch next (:pageSize) rows only", nativeQuery = true)
	public List<Assunto> buscarPorDemanda(@Param("texto") String texto, @Param("pageSize") Integer pageSize,@Param("currentPage") Integer currentPage);
	
	@Query( value="Select count(*) from assunto "
				+ "where (retorno) ILIKE LOWER(CONCAT('%', :texto, '%')) ", nativeQuery = true)
	public Integer buscarQuantidadeRegistro(@Param("texto") String texto);
	

}