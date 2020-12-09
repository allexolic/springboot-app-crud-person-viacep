package com.infoguia.gestaopessoa.repository;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.infoguia.gestaopessoa.model.Pessoa;

public interface Pessoas extends JpaRepository<Pessoa, Long> {

	@Modifying
	@Query(value = "update inf_pessoa set nm_pessoa = ?1, nu_cpf = ?2, address_id = ?3, "
			+ " nu_endereco = ?4, nm_complemento = ?5, nm_email = ?7, dt_nascimento = ?8"
			+ " where id = ?6",
	       nativeQuery=true)
	int atualizaPessoa(String nome, String nuCpf, Long address, String nuEndereco, String nmComplemento, Long id,
			           String email, Date dtNascimento);
	
	@Modifying
	@Query(value = "insert into inf_pessoa(nm_pessoa, nu_cpf, address_id, nu_endereco , nm_complemento, "
			+ " nm_email, dt_nascimento) "
			+ " values(?1, ?2, ?3, ?4, ?5, ?6, ?7)", 
			nativeQuery=true)	
	int InserirPessoa(String nome, String nuCpf, Long address, String nuEndereco, String nmComplemento,
	                  String email, Date dtNascimento);
	
	
	@Modifying
	@Query(value="update inf_pessoa set address_id = ?1 where id = ?2", nativeQuery=true)
	int atualizaPessoaCep(Long address, Long id);
}
