package com.hacademy.runner.service;

import java.io.IOException;

import com.hacademy.runner.entity.JavaSourceVO;
import com.hacademy.runner.exception.CodeCompileException;

public interface JavaCompileService {
	
	/**
	 * 클래스 없이 소스코드만 있을 경우의 컴파일
	 * @param sourceCode 소스코드
	 * @return 컴파일 클래스명
	 * @throws IOException 입출력 예외
	 * @throws CodeCompileException 컴파일 예외
	 */
	String simpleCompile(JavaSourceVO sourceVO) throws IOException, CodeCompileException;
	String execute(String className) throws IOException, InterruptedException;
	String simpleCompileAndExecute(JavaSourceVO sourceVO) throws IOException, InterruptedException, CodeCompileException;
}
