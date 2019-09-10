package com.prova.apollus.api.repository.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prova.apollus.api.model.Usuario;
import com.prova.apollus.api.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {

	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable);
	
	
}