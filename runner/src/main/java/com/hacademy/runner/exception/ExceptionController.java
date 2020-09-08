package com.hacademy.runner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionController {
	
	@ExceptionHandler(CodeCompileException.class)
	public ResponseEntity<String> codeCompileException(CodeCompileException e) {
		log.error("error", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
									.body(e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exception(Exception e) {
		log.error("error", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
												.body("Internal server error");
	}
	
}
