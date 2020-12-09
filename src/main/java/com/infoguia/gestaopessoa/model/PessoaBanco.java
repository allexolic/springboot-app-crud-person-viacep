package com.infoguia.gestaopessoa.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="inf_pessoa_conta_corrente")
public class PessoaBanco{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nuAgencia;
	
	private String nuContaCorrente;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)	
	@JoinColumn(name="pessoa_id")
	private Pessoa pessoa;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "banco_id")
	private Banco banco;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNuAgencia() {
		return nuAgencia;
	}

	public void setNuAgencia(String nuAgencia) {
		this.nuAgencia = nuAgencia;
	}

	public String getNuContaCorrente() {
		return nuContaCorrente;
	}

	public void setNuContaCorrente(String nuContaCorrente) {
		this.nuContaCorrente = nuContaCorrente;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	
	
	
	
}
