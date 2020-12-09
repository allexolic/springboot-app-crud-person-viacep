package com.infoguia.gestaopessoa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;



@Entity
@Table(name="inf_address")
@DynamicUpdate
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String nuCep;
	
	private String nmLogradouro;
	
	private String nmBairro;
	
	private String nmLocalidade;
	
	private String nmUf;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNuCep() {
		return nuCep;
	}

	public void setNuCep(String nuCep) {
		this.nuCep = nuCep;
	}

	public String getNmLogradouro() {
		return nmLogradouro;
	}

	public void setNmLogradouro(String nmLogradouro) {
		this.nmLogradouro = nmLogradouro;
	}

	public String getNmBairro() {
		return nmBairro;
	}

	public void setNmBairro(String nmBairro) {
		this.nmBairro = nmBairro;
	}

	public String getNmLocalidade() {
		return nmLocalidade;
	}

	public void setNmLocalidade(String nmLocalidade) {
		this.nmLocalidade = nmLocalidade;
	}

	public String getNmUf() {
		return nmUf;
	}

	public void setNmUf(String nmUf) {
		this.nmUf = nmUf;
	}	

}
