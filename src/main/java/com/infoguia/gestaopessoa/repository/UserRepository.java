package com.infoguia.gestaopessoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.infoguia.gestaopessoa.model.User;

public interface UserRepository  extends JpaRepository<User, Long>{

	User findByUsername(String username);
	
	@Modifying
	@Query(value="update inf_user set ativo = ?1 where id = ?2", nativeQuery=true)
	int atualizaUsuario(boolean ativo, Long id);
	
	@Modifying
	@Query(value="update inf_user_role set role_id = ?1 where user_id = ?2 and role_id <> ?1",
	       nativeQuery=true)
	int atualizaPerfilUsuario(Long idRole, Long idUser);
}
