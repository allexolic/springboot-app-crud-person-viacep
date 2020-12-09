package com.infoguia.gestaopessoa.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.infoguia.gestaopessoa.model.User;
import com.infoguia.gestaopessoa.repository.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		
		if(user == null)
			throw new UsernameNotFoundException(username);
		
		if(user.getAtivo() == 0) {
			
			throw new UsernameNotFoundException("Usuário Inativo");
		}
			
					
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				user.getPassword(), getAuthorities(user));
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		// TODO Auto-generated method stub
		String[] userRoles = user.getRoles().stream().map((role) -> role.getNome()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		
		return authorities;
	}
	

}


