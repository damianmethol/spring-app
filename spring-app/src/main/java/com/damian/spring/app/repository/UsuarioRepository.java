package com.damian.spring.app.repository;

//import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.damian.spring.app.entity.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{

	
}
