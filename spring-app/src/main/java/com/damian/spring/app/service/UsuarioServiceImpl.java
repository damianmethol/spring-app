package com.damian.spring.app.service;

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

}
