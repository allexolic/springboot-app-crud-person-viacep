package com.infoguia.gestaopessoa.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.infoguia.gestaopessoa.model.Role;
import com.infoguia.gestaopessoa.model.User;

import com.infoguia.gestaopessoa.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void salvar(User user) {
 
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		user.setRoles((List<Role>) user.getRoles());
		userRepository.save(user);
	}

	@Transactional
	public void editar(User user) {
		
		user.setRoles((List<Role>) user.getRoles());
		
		userRepository.atualizaUsuario(user.getAtivo(), user.getId());
		
		userRepository.atualizaPerfilUsuario(user.getRoles().get(0).getId(), user.getId());
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
}
