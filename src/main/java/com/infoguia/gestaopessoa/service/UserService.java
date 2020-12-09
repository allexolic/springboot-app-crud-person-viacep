package com.infoguia.gestaopessoa.service;

import com.infoguia.gestaopessoa.model.User;

public interface UserService {

	void salvar(User user);
	
	void editar(User user);
	
	User findByUsername(String username);

}
