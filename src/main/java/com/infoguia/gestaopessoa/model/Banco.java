package com.infoguia.gestaopessoa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "inf_banco")
public class Banco {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message="Campo obrigatório.")
	@NotEmpty(message="Campo obrigatório.")
	private String nmBanco;
		
	private String cdBanco;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNmBanco() {
		return nmBanco;
	}

	public void setNmBanco(String nmBanco) {
		this.nmBanco = nmBanco;
	}

	public String getCdBanco() {
		return cdBanco;
	}

	public void setCdBanco(String cdBanco) {
		this.cdBanco = cdBanco;
	}
	
	
}
