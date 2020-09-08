//package com.hacademy.runner;
//
//import java.io.IOException;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.hacademy.runner.exception.CodeCompileException;
//import com.hacademy.runner.service.JavaCompileService;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@SpringBootTest
//public class Test03 {
//	@Autowired
//	private JavaCompileService compileService;
//	
//	String sourceCode;
//
//	@BeforeEach
//	public void prepare() {
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("//First Java Program\n");
//		buffer.append("System.out.println(\"Hello World!\");\n");
//
//		sourceCode = buffer.toString();
//	}
//	
//	@Test
//	public void test() throws IOException, InterruptedException, CodeCompileException {
//		String result = compileService.simpleCompileAndExecute(sourceCode);
//		log.debug("\n{}", result);
//	}
//}
