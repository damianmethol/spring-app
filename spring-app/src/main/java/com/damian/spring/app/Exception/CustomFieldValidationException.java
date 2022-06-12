package com.damian.spring.app.Exception;

public class CustomFieldValidationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3484695086571215208L;
	
	private String fieldName;
	
	public CustomFieldValidationException(String message, String fieldName) {
		super(message);
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}
}
