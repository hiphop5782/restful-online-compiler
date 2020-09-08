package com.hacademy.runner.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacademy.runner.entity.JavaSourceVO;
import com.hacademy.runner.exception.CodeCompileException;
import com.hacademy.runner.service.JavaCompileService;

@CrossOrigin(origins="*")
//@CrossOrigin(origins= {"http://www.sysout.co.kr", "http://localhost/"})
@RestController
@RequestMapping("/java")
public class JavaCompileController {
	
	@Autowired
	private JavaCompileService javaCompileService;
	
	@PostMapping("/simple")
	public String compileWithoutMain(@RequestBody JavaSourceVO sourceVO) throws IOException, InterruptedException, CodeCompileException {
		return javaCompileService.compileAndExecuteWithoutMainMethod(sourceVO);
	}
	
	@PostMapping("/main")
	public String compileWithMain(@RequestBody JavaSourceVO sourceVO) throws IOException, InterruptedException, CodeCompileException {
		return javaCompileService.compileAndExecuteWithMainMethod(sourceVO);
	}
}
