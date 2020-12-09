package com.infoguia.gestaopessoa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, proxyTargetClass=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Qualifier("userDetailsServiceImpl")
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth
			.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager userAuthenticationManager() throws Exception{
		return authenticationManager();
	}
	
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http
			.headers()
				.frameOptions().sameOrigin()
				.and()
			.authorizeRequests()
				.antMatchers("/resources/**", "/webjars/**", "/assets/**").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/usuario").hasRole("ADMIN")
				.antMatchers("/pessoas").hasAnyRole("ADMIN","USER")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/home")
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login");
	}
	
}
