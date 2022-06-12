package com.damian.spring.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.damian.spring.app.Exception.CustomFieldValidationException;
import com.damian.spring.app.Exception.UsernameOrIdNotFound;
import com.damian.spring.app.dto.ChangePasswordForm;
import com.damian.spring.app.entity.Usuario;
import com.damian.spring.app.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Iterable<Usuario> getAllUsuarios() {
		return repository.findAll();
	}
	
	
	
	private boolean checkUsernameAvailable(Usuario user) throws Exception {
		Optional<Usuario> userFound = repository.findByUsername(user.getUsername());
		if(userFound.isPresent()) {
			throw new CustomFieldValidationException("Username no disponible", "username");
		}
		return true;
	}

	private boolean checkPasswordValid(Usuario user) throws Exception {
		
		if(user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
			throw new CustomFieldValidationException("Confirm Password es obligatorio", "confirmPassword");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			throw new CustomFieldValidationException("Password y Confirm Password no son iguales", "password");
		}
		return true;
	}



	@Override
	public Usuario createUsuario(Usuario usuario) throws Exception {
		if(checkUsernameAvailable(usuario) && checkPasswordValid(usuario)) {
			
			String encodedPassword = bCryptPasswordEncoder.encode(usuario.getPassword());
			usuario.setPassword(encodedPassword);
			
			usuario = repository.save(usuario);
		}
		return usuario;
	}



	@Override
	public Usuario getUserById(Long id) throws UsernameOrIdNotFound {
		return repository.findById(id).orElseThrow(() -> new UsernameOrIdNotFound("El ID del usuario no existe."));
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
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteUser(Long id) throws UsernameOrIdNotFound {
		Usuario user = getUserById(id);
		
		repository.delete(user);
	}
	
	public boolean isLoggedUserADMIN(){
		 return loggedUserHasRole("ROLE_ADMIN");
		}

	public boolean loggedUserHasRole(String role) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = null;
		Object roles = null; 
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
			
			roles = loggedUser.getAuthorities().stream()
						.filter(x -> role.equals(x.getAuthority()))
						.findFirst().orElse(null); //loggedUser = null;
		}
		return roles != null ?true :false;
		}
	
	@Override
	public Usuario changePassword(ChangePasswordForm form) throws Exception {
		
		Usuario user = getUserById(form.getId());
		
		if(!isLoggedUserADMIN() && !user.getPassword().equals(form.getCurrentPassword())) {
			throw new Exception("Current Password inválido.");
		}
		if(user.getPassword().equals(form.getNewPassword())) {
			throw new Exception("Nueva password debe ser diferente a la password actual.");
		}
		if(!form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("Nueva Password y Confirm Password no coinciden.");
		}
		
		String encodedPassword = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setPassword(encodedPassword);
		return repository.save(user);
	}
	
	public Usuario getLoggedUser() throws Exception {
		//Obtener el usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;

		//Verificar que ese objeto traido de sesion es el usuario
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		}
		
		Usuario myUser = repository
				.findByUsername(loggedUser.getUsername()).orElseThrow(() -> new Exception("Problemas obteniendo usuario de sesión"));
		
		return myUser;
	}
	
}
