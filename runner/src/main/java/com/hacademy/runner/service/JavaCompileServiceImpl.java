package com.hacademy.runner.service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacademy.runner.entity.JavaSourceVO;
import com.hacademy.runner.exception.CodeCompileException;
import com.hacademy.runner.unit.JavaSourceCodeObject;

@Service
public class JavaCompileServiceImpl implements JavaCompileService{

	@Autowired
	private ProcessRunner runner;
	
	@Autowired
	private GenerateStringService gsService;
	
	@Override
	public String simpleCompile(JavaSourceVO sourceVO) throws IOException, CodeCompileException {
		//create java compiler
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		//create diagnostic listener(특수 증상 기록도구) 생성
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		
		//java file manager(현재 없어도 됨)
		JavaFileManager manager = compiler.getStandardFileManager(diagnostics, Locale.KOREA, Charset.forName("UTF-8"));

		//generate class name
		String className = gsService.generateTestClassName(20);
		
		//wrapping class code
		StringBuffer buffer = new StringBuffer();
		buffer.append("import java.lang.*;\n");
		buffer.append("public class ");
		buffer.append(className);
		buffer.append("{\n");
		buffer.append("public static void main(String[] args){\n");
		buffer.append(sourceVO.getCode());
		buffer.append("\n");
		buffer.append("}\n");
		buffer.append("}");
		
		//string writer for receive compile output message
		StringWriter writer = new StringWriter();
		
		//compile object list
		List<? extends JavaFileObject> srcList = List.of(new JavaSourceCodeObject(className, buffer.toString()));
		
		//compile option list
		List<String> options = List.of("-d", "./src/main/resources/compile");
		
		//compile task
		JavaCompiler.CompilationTask task = compiler.getTask(writer, manager, diagnostics, options, null, srcList);
		
		//throw error if task fail
		if(!task.call()) {
			StringBuffer err = new StringBuffer();
			for(Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
				err.append(diagnostic);
			}
			throw new CodeCompileException(err.toString());
		}
		
		//get output message
		//String output = writer.toString();
		
		//manager close
		manager.close();
		
		//return class name
		return className;
	}

	@Override
	public String execute(String className) throws IOException, InterruptedException {
		String javaHome = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java.exe";
		String[] command = new String[] {javaHome, className};
		return runner.byProcessBuilderOnDelete(command, className);
	}

	@Override
	public String simpleCompileAndExecute(JavaSourceVO sourceVO) throws IOException, InterruptedException, CodeCompileException {
		// TODO Auto-generated method stub
		return execute(simpleCompile(sourceVO));
	}


}
