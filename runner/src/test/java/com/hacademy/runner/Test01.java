package com.hacademy.runner;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class Test01 {

	String sourceCode;

	@BeforeEach
	public void prepare() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("public class Test{\n");
		buffer.append("	public static void main(String[] args){\n");
		buffer.append("		//First Java Program\n");
		buffer.append("		System.out.println(\"Hello World!\");\n");
		buffer.append("	}\n");
		buffer.append("}\n");

		sourceCode = buffer.toString();
	}

	@Test
	public void test() throws Exception{
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

		List<? extends JavaFileObject> srcList = List.of(new JavaSourceCodeObject("Test", sourceCode));
		JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, srcList);
		boolean result = task.call();
		log.debug("result = {}", result);
		if(!result) {
			for(Diagnostic<? extends JavaFileObject> d : diagnostics.getDiagnostics()) {
				System.out.println(d);
			}
			return;
		}
		
		Class<?> clazz = Class.forName("Test");
	}

}

class JavaSourceCodeObject extends SimpleJavaFileObject {
	final String code;

	JavaSourceCodeObject(String name, String code) {
		super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
		this.code = code;
	}

	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return code;
	}
}
