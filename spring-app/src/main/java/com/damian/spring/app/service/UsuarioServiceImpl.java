package com.damian.spring.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damian.spring.app.entity.Usuario;
import com.damian.spring.app.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioRepository repository;
	
	@Override
	public Iterable<Usuario> getAllUsuarios() {
		return repository.findAll();
	}
	
	
	
	private boolean checkUsernameAvailable(Usuario user) throws Exception {
		Optional<Usuario> userFound = repository.findByUsername(user.getUsername());
		if(userFound.isPresent()) {
			throw new Exception("Username no disponible");
		}
		return true;
	}

	private boolean checkPasswordValid(Usuario user) throws Exception {
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("Password y Confirm Password no son iguales");
		}
		return true;
	}



	@Override
	public Usuario createUsuario(Usuario usuario) throws Exception {
		if(checkUsernameAvailable(usuario) && checkPasswordValid(usuario)) {
			usuario = repository.save(usuario);
		}
		return usuario;
	}
	
}
