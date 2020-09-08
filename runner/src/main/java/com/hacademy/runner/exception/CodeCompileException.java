package com.hacademy.runner.exception;

public class CodeCompileException extends Exception{
	
	public CodeCompileException() {
		super("Code compile error");
	}
	
	public CodeCompileException(String message) {
		super(message);
	}
}
