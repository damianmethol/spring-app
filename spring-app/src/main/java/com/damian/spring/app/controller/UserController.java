package com.damian.spring.app.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.damian.spring.app.Exception.UsernameOrIdNotFound;
import com.damian.spring.app.dto.ChangePasswordForm;
import com.damian.spring.app.entity.Usuario;
import com.damian.spring.app.repository.RoleRepository;
import com.damian.spring.app.service.UsuarioService;

@Controller
public class UserController {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping({"/", "/login"}) 
	public String index() {
		return "index";
	}

	@GetMapping("/userForm")
	public String userForm (Model model) {
		
		model.addAttribute("userForm", new Usuario());
		model.addAttribute("userList", usuarioService.getAllUsuarios());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("listTab", "active");
		
		return "user-form/user-view";
	}
	
	@PostMapping("/userForm")
	public String crearUsuario(@Valid @ModelAttribute("userForm") Usuario usuario, BindingResult result, ModelMap model) {
		
		if(result.hasErrors()) {
			model.addAttribute("userForm", usuario);
			model.addAttribute("formTab", "active");
		} else {
			try {
				usuarioService.createUsuario(usuario);
				model.addAttribute("userForm", new Usuario());
				model.addAttribute("listTab", "active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", usuario);
				model.addAttribute("formTab", "active");
				model.addAttribute("userList", usuarioService.getAllUsuarios());
				model.addAttribute("roles", roleRepository.findAll());
			}
		}
		
		model.addAttribute("userList", usuarioService.getAllUsuarios());
		model.addAttribute("roles", roleRepository.findAll());
		
		return "user-form/user-view";
	}
	
	@GetMapping("/editUser/{id}")
	public String getEditUserForm(Model model, @PathVariable(name= "id") Long id) throws Exception {
		Usuario userToEdit = usuarioService.getUserById(id);
		
		model.addAttribute("userForm", userToEdit);
		model.addAttribute("userList", usuarioService.getAllUsuarios());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("formTab", "active");
		model.addAttribute("editMode", "true");
		model.addAttribute("passwordForm",new ChangePasswordForm(id));
		
		return "user-form/user-view";
	}
	
	@PostMapping("/editUser")
	public String postEditUserForm(@Valid @ModelAttribute("userForm") Usuario usuario, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm", usuario);
			model.addAttribute("formTab", "active");
			model.addAttribute("editMode", "true");
			model.addAttribute("passwordForm",new ChangePasswordForm(usuario.getId()));
		} else {
			try {
				usuarioService.updateUser(usuario);
				model.addAttribute("userForm", new Usuario());
				model.addAttribute("listTab", "active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", usuario);
				model.addAttribute("formTab", "active");
				model.addAttribute("userList", usuarioService.getAllUsuarios());
				model.addAttribute("roles", roleRepository.findAll());
				model.addAttribute("editMode", "true");
				model.addAttribute("passwordForm",new ChangePasswordForm(usuario.getId()));
			}
		}
		
		model.addAttribute("userList", usuarioService.getAllUsuarios());
		model.addAttribute("roles", roleRepository.findAll());
		
		return "user-form/user-view";
		
	}
	
	@GetMapping("/userForm/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/userForm";
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(Model model, @PathVariable(name="id") Long id) {
		try {
			usuarioService.deleteUser(id);
		} catch (UsernameOrIdNotFound e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		return userForm(model);
	}
	
	@PostMapping("/editUser/changePassword")
	public ResponseEntity<String> postEditUserChangePassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {
		try {
			if(errors.hasErrors()) {
				String result = errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(""));

            throw new Exception(result);
			}
			usuarioService.changePassword(form);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success"); 
	}
}
