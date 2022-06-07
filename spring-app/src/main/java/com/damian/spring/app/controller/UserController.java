package com.damian.spring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.damian.spring.app.entity.Usuario;
import com.damian.spring.app.repository.RoleRepository;
import com.damian.spring.app.service.UsuarioService;

@Controller
public class UserController {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/")
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
	
}
