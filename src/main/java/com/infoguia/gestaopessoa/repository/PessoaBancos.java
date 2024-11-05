package com.infoguia.gestaopessoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.infoguia.gestaopessoa.model.PessoaBanco;

import java.util.List;

public interface PessoaBancos extends JpaRepository<PessoaBanco, Long>{

	@Query(value="select * From inf_pessoa_conta_corrente where pessoa_id = ?1", nativeQuery=true)
	List<PessoaBanco> findByPessoaId(Long id);
}
