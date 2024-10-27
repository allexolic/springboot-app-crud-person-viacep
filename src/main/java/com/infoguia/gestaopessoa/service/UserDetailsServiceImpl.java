package com.infoguia.gestaopessoa.service;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{

	private UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		
		if(user == null) {
			log.error("User not found");
			throw new UsernameNotFoundException(username);
		}
		if(!user.getAtivo()) {
			log.warn("Inactive user trying access the app: " + username);
			throw new UsernameNotFoundException("User deactivated: " + username);
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


