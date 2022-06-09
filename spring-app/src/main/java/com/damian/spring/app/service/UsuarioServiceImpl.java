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
		
		if(user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
			throw new Exception("Confirm Password es obligatorio ");
		}
		
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



	@Override
	public Usuario getUserById(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new Exception("El usuario no existe."));
	}



	@Override
	public Usuario updateUser(Usuario usuarioOrigen) throws Exception {
		Usuario usuarioDestino = getUserById(usuarioOrigen.getId());
		mapUser(usuarioOrigen, usuarioDestino);
		return repository.save(usuarioDestino);
	}
	
	/**
	 * Mapea todo menos la contraseña
	 * @param origen
	 * @param destino
	 */
	protected void mapUser(Usuario origen, Usuario destino) {
		destino.setUsername(origen.getUsername());
		destino.setNombre(origen.getNombre());
		destino.setApellido(origen.getApellido());
		destino.setEmail(origen.getEmail());
		destino.setRoles(origen.getRoles());
	}



	@Override
	public void deleteUser(Long id) throws Exception {
		Usuario user = getUserById(id);
		
		repository.delete(user);
		
	}
	
}
