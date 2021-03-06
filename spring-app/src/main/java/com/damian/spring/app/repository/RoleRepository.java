package com.damian.spring.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.damian.spring.app.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	public Role findByName(String name);
	
}
