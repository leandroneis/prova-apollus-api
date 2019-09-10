package com.prova.apollus.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.prova.apollus.api.event.RecursoCriadoEvent;
import com.prova.apollus.api.exceptionhandler.ProvaApollusApiExceptionHandler.Erro;
import com.prova.apollus.api.model.Usuario;
import com.prova.apollus.api.repository.UsuarioRepository;
import com.prova.apollus.api.repository.filter.UsuarioFilter;
import com.prova.apollus.api.service.UsuarioService;
import com.prova.apollus.api.service.exception.UsuarioEmailExistenteException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER') and #oauth2.hasScope('read')")
	public Page<Usuario> pesquisar(UsuarioFilter usuarioFilter, Pageable pageable) {
		return usuarioRepository.filtrar(usuarioFilter, pageable);
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAnyRole('ADMIN','USER') and #oauth2.hasScope('read')")
	public ResponseEntity<Usuario> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Usuario> usuario = usuarioRepository.findById(codigo);
		return usuario.isPresent() ? ResponseEntity.ok(usuario.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {
		Usuario pessoaSalvo = usuarioService.salvar(usuario);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalvo);
	}
	
	@ExceptionHandler({ UsuarioEmailExistenteException.class })
	public ResponseEntity<Object> handleUsuarioEmailExistenteException(UsuarioEmailExistenteException ex) {
		String mensagemUsuario = messageSource.getMessage("usuario.email.existente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasRole('ADMIN') and #oauth2.hasScope('write')")
	public @ResponseBody ResponseEntity<String> remover(@PathVariable Long codigo) {
		usuarioRepository.deleteById(codigo);
	    return new ResponseEntity<String>("Usuário apagado com sucesso!", HttpStatus.NO_CONTENT);
	}
	
	
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasRole('ADMIN') and #oauth2.hasScope('write')")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long codigo, @Valid @RequestBody Usuario usuario) {
		try {
			Usuario pessoaSalvo = usuarioService.atualizar(codigo, usuario);
			return ResponseEntity.ok(pessoaSalvo);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}