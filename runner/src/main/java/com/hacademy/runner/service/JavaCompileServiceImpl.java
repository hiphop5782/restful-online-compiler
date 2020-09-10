package com.hacademy.runner.service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacademy.runner.entity.JavaResultVO;
import com.hacademy.runner.entity.JavaSourceVO;
import com.hacademy.runner.exception.CodeCompileException;
import com.hacademy.runner.unit.JavaSourceCodeObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JavaCompileServiceImpl implements JavaCompileService{

	@Autowired
	private ProcessRunner runner;
	
	@Autowired
	private GenerateStringService gsService;
	
	@Override
	public JavaResultVO compileWithoutMainMethod(JavaSourceVO sourceVO) throws IOException, CodeCompileException {
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
		
		sourceVO.setCode(buffer.toString());
		
		return compileWithMainMethod(sourceVO);
	}

	@Override
	public JavaResultVO execute(JavaResultVO resultVO) throws IOException, InterruptedException {
		String javaHome = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java.exe";
		String[] command = new String[] {javaHome, resultVO.getClassName()};
		
		//check time
		resultVO.setExecuteTime(LocalDateTime.now());
		
		//check elapsed time
		long start = System.currentTimeMillis();
		String result = runner.byProcessBuilderOnDelete(command, resultVO.getClassName());
		long finish = System.currentTimeMillis();
		resultVO.setResult(result);
		resultVO.setExecuteElapsed(finish - start);
		return resultVO;
	}

	@Override
	public JavaResultVO compileAndExecuteWithoutMainMethod(JavaSourceVO sourceVO) throws IOException, InterruptedException, CodeCompileException {
		// TODO Auto-generated method stub
		return execute(compileWithoutMainMethod(sourceVO));
	}

	@Override
	public JavaResultVO compileWithMainMethod(JavaSourceVO sourceVO) throws IOException, CodeCompileException {
		//create java compiler
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		//create diagnostic listener(특수 증상 기록도구) 생성
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		
		//java file manager(현재 없어도 됨)
		JavaFileManager manager = compiler.getStandardFileManager(diagnostics, Locale.KOREA, Charset.forName("UTF-8"));

		//find class name
		Matcher matcher = Pattern.compile("public\\s+class\\s+(\\w+)\\s*\\{").matcher(sourceVO.getCode());
		matcher.find();
		String className = matcher.group(1);
		
		//string writer for receive compile output message
		StringWriter writer = new StringWriter();
		
		//compile object list
		List<? extends JavaFileObject> srcList = List.of(new JavaSourceCodeObject(className, sourceVO.getCode()));
		
		//compile option list
		List<String> options = List.of("-d", runner.getBaseDirectory().getAbsolutePath());
		
		//compile task
		LocalDateTime compileTime = LocalDateTime.now();
		long start = System.currentTimeMillis();
		JavaCompiler.CompilationTask task = compiler.getTask(writer, manager, diagnostics, options, null, srcList);
		long finish = System.currentTimeMillis();
		
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
		
		//return result data
		JavaResultVO resultVO = JavaResultVO.builder()
												.code(sourceVO.getCode())
												.className(className)
												.compileTime(compileTime)
												.compileElapsed(finish - start)
											.build();
		log.info("compile result = {}", resultVO);
		return resultVO;
	}

	@Override
	public JavaResultVO compileAndExecuteWithMainMethod(JavaSourceVO sourceVO)
			throws IOException, InterruptedException, CodeCompileException {
		return execute(compileWithMainMethod(sourceVO));
	}


}
