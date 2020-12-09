package com.infoguia.gestaopessoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.infoguia.gestaopessoa.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

	Address findByNuCep(String nuCep);
	
	@Query(value="select id from inf_address where nu_cep = ?1", nativeQuery=true)
	Long cepId(String nuCep);
	
}
