package com.infoguia.gestaopessoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoguia.gestaopessoa.model.Role;

public interface Permissoes extends JpaRepository<Role, Long>{

}
