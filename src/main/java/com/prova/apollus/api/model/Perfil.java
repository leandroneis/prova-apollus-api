package com.prova.apollus.api.model;

import org.springframework.security.core.GrantedAuthority;

public enum Perfil implements GrantedAuthority{

	ROLE_ADMIN,ROLE_USER;
	
	@Override
	public String getAuthority() {
		return name();
	}
	
	
}