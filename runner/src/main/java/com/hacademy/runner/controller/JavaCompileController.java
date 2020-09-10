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
	
	@PostMapping(value="/simple", produces = "application/json; charset=UTF-8")
	public JavaResultVO compileWithoutMain(@RequestBody JavaSourceVO sourceVO) throws IOException, InterruptedException, CodeCompileException {
		log.debug("/simple request : {}", sourceVO);
		JavaResultVO resultVO = javaCompileService.compileAndExecuteWithoutMainMethod(sourceVO);
		log.debug("/simple response : {}", resultVO);
		return resultVO;
	}

	@PostMapping(value="/main", produces = "application/json; charset=UTF-8")
	public JavaResultVO compileWithMain(@RequestBody JavaSourceVO sourceVO) throws IOException, InterruptedException, CodeCompileException {
		log.debug("/main request : {}", sourceVO);
		JavaResultVO resultVO = javaCompileService.compileAndExecuteWithMainMethod(sourceVO);
		log.debug("/main response : {}", resultVO);
		return resultVO;
	}
}
