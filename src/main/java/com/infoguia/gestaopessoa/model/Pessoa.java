package com.infoguia.gestaopessoa.model;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "inf_pessoa")
@DynamicUpdate
public class Pessoa implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nm_pessoa", length = 175, nullable = false)
	private String nome;

	@Column(name = "nu_cpf", length = 14, nullable = false, unique = true)
	private String nuCpf;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;
	
	private String nmComplemento;
	
	@Column(length=6)
	private String nuEndereco;
	
	private String nmEmail;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dtNascimento;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNuCpf() {
		return nuCpf;
	}

	public void setNuCpf(String nuCpf) {
		this.nuCpf = nuCpf;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getNmComplemento() {
		return nmComplemento;
	}

	public void setNmComplemento(String nmComplemento) {
		this.nmComplemento = nmComplemento;
	}

	public String getNuEndereco() {
		return nuEndereco;
	}

	public void setNuEndereco(String nuEndereco) {
		this.nuEndereco = nuEndereco;
	}

	public String getNmEmail() {
		return nmEmail;
	}

	public void setNmEmail(String nmEmail) {
		this.nmEmail = nmEmail;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	
	
	
}
