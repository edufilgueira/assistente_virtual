package com.ia.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;



@Entity
public class Assunto {

	@Id
	@SequenceGenerator(name = "assunto_seq", sequenceName = "assunto_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assunto_seq")
	private Integer id;
	
	private String retorno;
	
	private Integer status = 1;
	
	/*@OneToMany(cascade=CascadeType.ALL)
	private List<Interacao> interacao = new ArrayList<>();*/
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRetorno() {
		return retorno;
	}
	public void setRetorno(String descricao) {
		this.retorno = descricao;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
