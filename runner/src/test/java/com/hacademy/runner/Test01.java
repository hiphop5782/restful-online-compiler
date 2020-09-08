//package com.hacademy.runner;
//
//import java.io.File;
//import java.io.StringWriter;
//import java.lang.reflect.Method;
//import java.net.URI;
//import java.net.URL;
//import java.net.URLClassLoader;
//import java.nio.charset.Charset;
//import java.util.List;
//import java.util.Locale;
//
//import javax.tools.Diagnostic;
//import javax.tools.DiagnosticCollector;
//import javax.tools.JavaCompiler;
//import javax.tools.JavaFileManager;
//import javax.tools.JavaFileObject;
//import javax.tools.SimpleJavaFileObject;
//import javax.tools.ToolProvider;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@SpringBootTest
//public class Test01 {
//
//	String sourceCode;
//
//	@BeforeEach
//	public void prepare() {
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("public class Test{\n");
//		buffer.append("	public static void main(String[] args){\n");
//		buffer.append("		//First Java Program\n");
//		buffer.append("		System.out.println(\"Hello World!\");\n");
//		buffer.append("	}\n");
//		buffer.append("}\n");
//
//		sourceCode = buffer.toString();
//	}
//
//	@Test
//	public void test() throws Exception{
//		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//		
//		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
//		JavaFileManager manager = compiler.getStandardFileManager(diagnostics, Locale.KOREA, Charset.forName("UTF-8"));
//		StringWriter writer = new StringWriter();
//		List<? extends JavaFileObject> srcList = List.of(new JavaSourceCodeObject("Test", sourceCode));
//		List<String> options = List.of("-d", "./src/main/resources/compile");
//		JavaCompiler.CompilationTask task = compiler.getTask(writer, manager, diagnostics, options, null, srcList);
//		boolean result = task.call();
//		log.debug("result = {}", result);
//		if(!result) {
//			log.error("Error occured");
//			for(Diagnostic<? extends JavaFileObject> d : diagnostics.getDiagnostics()) {
//				System.out.println(d);
//			}
//			return;
//		}
//		
//		String output = writer.toString();
//		log.debug("{}", output);
//		
////		dynamic execute in same thread
//		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { new File("./src/main/resources/compile").toURI().toURL() });
//		Class<?> mainClass = Class.forName("Test", true, classLoader);
////		for(Method method : mainClass.getMethods()) {
////			log.debug("method = {}", method.getName());
////		}
//		Method method = mainClass.getMethod("main", String[].class);
//		String[] params = null;
//		method.invoke(null, (Object)params);
//		
//		manager.close();
//	}
//	
//	class JavaSourceCodeObject extends SimpleJavaFileObject {
//		final String code;
//
//		JavaSourceCodeObject(String name, String code) {
//			super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
//			this.code = code;
//		}
//
//		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
//			return code;
//		}
//	}
//
//}
//
//
