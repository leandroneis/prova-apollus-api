package com.prova.apollus.api.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.prova.apollus.api.model.Usuario;
import com.prova.apollus.api.repository.UsuarioRepository;
import com.prova.apollus.api.service.exception.UsuarioEmailExistenteException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario salvar(Usuario usuario) {
		if (verificaDuplicidadeEmailAoSalvar(usuario)) {
			throw new UsuarioEmailExistenteException();
		} else {
			return usuarioRepository.save(usuario);
		}
	}

	public Usuario atualizar(Long codigo, Usuario usuario) {
		Usuario usuarioSalvo = buscarUsuarioPeloCodigo(codigo);
		if (verificaDuplicidadeEmailAoAtualizar(usuarioSalvo.getCodigo(), usuario)) {
			throw new UsuarioEmailExistenteException();
		} else {
			BeanUtils.copyProperties(usuario, usuarioSalvo, "codigo");
			return usuarioRepository.save(usuarioSalvo);
		}
	}

	private Boolean verificaDuplicidadeEmailAoAtualizar(Long codigo, Usuario usuario) {
		Usuario usuarioEmail = buscaUsuarioPorEmail(usuario);
		if (usuarioEmail != null && !usuarioEmail.getCodigo().equals(codigo)) {
			return true;
		}
		return false;
	}

	private Boolean verificaDuplicidadeEmailAoSalvar(Usuario usuario) {
		if (usuario != null) {
			try {
				Usuario usuarioEmail = buscaUsuarioPorEmail(usuario);

				if (usuarioEmail != null) {
					return true;
				}
			} catch (NoSuchElementException e) {
				return false;
			}

		}
		return false;
	}

	private Usuario buscaUsuarioPorEmail(Usuario usuario) {
		Optional<Usuario> usuarioSalvo = usuarioRepository.findByEmail(usuario.getEmail());
		return usuarioSalvo.get();
	}

	private Usuario buscarUsuarioPeloCodigo(Long codigo) {
		Optional<Usuario> usuarioSalvo = usuarioRepository.findById(codigo);
		if (!usuarioSalvo.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return usuarioSalvo.get();
	}

}
