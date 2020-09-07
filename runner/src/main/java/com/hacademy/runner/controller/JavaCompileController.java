package com.hacademy.runner.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/java")
public class JavaCompileController {
	
	@PostMapping("/")
	public String compile() {
		return "Hello";
	}
}
