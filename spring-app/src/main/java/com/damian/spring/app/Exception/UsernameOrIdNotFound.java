package com.damian.spring.app.Exception;

public class UsernameOrIdNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -94644722378954983L;

	public UsernameOrIdNotFound() {
		super("Usuario o ID no encontrado");
	}
	
	public UsernameOrIdNotFound(String message) {
		super(message);
	}
}
