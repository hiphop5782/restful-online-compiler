//package com.hacademy.runner;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.io.StringWriter;
//import java.net.URI;
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
//public class Test02 {
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
//		String path = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java.exe";
//		File java = new File(path);
//		File target = new File("src/main/resources/compile/Test.class");
//		
//		ProcessRunner runner = new ProcessRunner();
//		String[] command = new String[] {java.getAbsolutePath(), "Test"};
//		String executeResult = runner.byProcessBuilder(command, target.getParentFile());
//		System.out.println("execute result");
//		System.out.print(executeResult);
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
//	class ProcessRunner {  
//	    public String byRuntime(String[] command)
//	                throws IOException, InterruptedException {
//	        Runtime runtime = Runtime.getRuntime();
//	        Process process = runtime.exec(command);
//	        return printStream(process);
//	    }
//
//	    public String byProcessBuilder(String[] command, File dir)
//	                throws IOException,InterruptedException {
//	        ProcessBuilder builder = new ProcessBuilder(command);
//	        builder.directory(dir);
//	        System.out.println("builder directory");
//	        System.out.println(builder.directory());
//	        Process process = builder.start();
//	        return printStream(process);
//	    }
//
//	    private String printStream(Process process)
//	                throws IOException, InterruptedException {
//	        process.waitFor();
//	        StringWriter writer = new StringWriter();
//	        try (InputStream psout = process.getInputStream()) {
//	            copy(psout, writer);
//	        }
//	        return writer.toString();
//	    }
//
//	    public void copy(InputStream input, StringWriter writer) throws IOException {
//	        char[] buffer = new char[1024];
//	        try(Reader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"))) {
//	        	int n;
//	        	while ((n = reader.read(buffer)) != -1) {
//	        		writer.write(buffer, 0, n);
//	        	}
//	        }
//	    }
//	}
//
//}
//
//
