package com.damian.spring.app.service;

import com.damian.spring.app.Exception.UsernameOrIdNotFound;
import com.damian.spring.app.dto.ChangePasswordForm;
import com.damian.spring.app.entity.Usuario;

public interface UsuarioService {
	
	public Iterable<Usuario> getAllUsuarios();

	public Usuario createUsuario(Usuario usuario) throws Exception;

	public Usuario getUserById(Long id) throws Exception;
	
	public Usuario updateUser (Usuario usuario) throws Exception;
	
	public void deleteUser(Long id) throws UsernameOrIdNotFound;
	
	public Usuario changePassword(ChangePasswordForm form) throws Exception;
	
}
