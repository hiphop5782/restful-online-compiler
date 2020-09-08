package com.hacademy.runner.unit;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class JavaSourceCodeObject extends SimpleJavaFileObject{
	private final String code;

	public JavaSourceCodeObject(String name, String code) {
		super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
		this.code = code;
	}

	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return code;
	}
}
