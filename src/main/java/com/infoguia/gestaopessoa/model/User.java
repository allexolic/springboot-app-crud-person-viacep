package com.infoguia.gestaopessoa.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="inf_user")
@DynamicUpdate
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Informe um usuário!")
	@NotEmpty(message = "Informe um usuário!")
	@Column(nullable=false)
	private String username;
	
	@NotNull(message = "Informe a senha!")
	@NotEmpty(message = "Informe a senha!")
	@Column(nullable=false, updatable=false)
	private String password;
	
	private boolean ativo;
	
	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(name="inf_user_role",
			   joinColumns= {@JoinColumn(name="user_id", referencedColumnName="id")},
			   inverseJoinColumns= {@JoinColumn(name="role_id", referencedColumnName="id")})
	private List<Role> roles;


	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	
}
