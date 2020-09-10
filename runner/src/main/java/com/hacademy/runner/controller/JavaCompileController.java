package com.hacademy.runner.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacademy.runner.entity.JavaResultVO;
import com.hacademy.runner.entity.JavaSourceVO;
import com.hacademy.runner.exception.CodeCompileException;
import com.hacademy.runner.service.JavaCompileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/java")
public class JavaCompileController {
	
	@Autowired
	private JavaCompileService javaCompileService;
	
	@PostMapping("/simple")
	public JavaResultVO compileWithoutMain(@RequestBody JavaSourceVO sourceVO) throws IOException, InterruptedException, CodeCompileException {
		log.info("/simple request : {}", sourceVO);
		return javaCompileService.compileAndExecuteWithoutMainMethod(sourceVO);
	}

	@PostMapping("/main")
	public JavaResultVO compileWithMain(@RequestBody JavaSourceVO sourceVO) throws IOException, InterruptedException, CodeCompileException {
		log.info("/main request : {}", sourceVO);
		return javaCompileService.compileAndExecuteWithMainMethod(sourceVO);
	}
}
