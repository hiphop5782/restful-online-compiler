package com.hacademy.runner.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProcessRunner {
	@Getter
	private final File baseDirectory = new File("D:/temp-compile");
	
	public String byRuntime(String[] command) throws IOException, InterruptedException {
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(command);
		return printStream(process);
	}

	public String byProcessBuilder(String[] command) throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.directory(baseDirectory);
		Process process = builder.start();
		log.debug("builder path = {}", builder.directory().getAbsolutePath());
		log.debug("command = {}", Arrays.deepToString(command));
		return printStream(process);
	}
	
	public String byProcessBuilderOnDelete(String[] command, String className) throws IOException, InterruptedException {
		String result = byProcessBuilder(command);
		File target = new File(baseDirectory, className+".class");
		target.delete();
		return result;
	}

	private String printStream(Process process) throws IOException, InterruptedException {
		process.waitFor();
		StringWriter writer = new StringWriter();
		try (InputStream psout = process.getInputStream()) {
			copy(psout, writer);
		}
		log.debug("printStream = {}", writer.toString());
		return writer.toString();
	}

	public void copy(InputStream input, StringWriter writer) throws IOException {
		char[] buffer = new char[1024];
		try (Reader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"))) {
			while (true) {
				int n = reader.read(buffer);
				log.debug("read = {}", n);
				if(n == -1) break;
				writer.write(buffer, 0, n);
			}
		}
	}
}
