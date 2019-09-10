package com.prova.apollus.api.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prova.apollus.api.model.Usuario;
import com.prova.apollus.api.repository.usuario.UsuarioRepositoryQuery;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {
	public Optional<Usuario> findByEmail(String email);


}
