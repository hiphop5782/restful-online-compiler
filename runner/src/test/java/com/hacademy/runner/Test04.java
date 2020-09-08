//package com.hacademy.runner;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class Test04 {
//	String sourceCode;
//
//	@BeforeEach
//	public void prepare() {
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("import java.lang.*;");
//		buffer.append("public class Test63307623558524372375{");
//		buffer.append("public static void main(String[] args){");
//		buffer.append("System.out.println(\"Hello Everybody\");");
//		buffer.append("}");
//		buffer.append("}");
//
//		sourceCode = buffer.toString();
//	}
//	
//	@Test
//	public void test() {
//		Matcher matcher = Pattern.compile("public\\s+class\\s+(\\w+)\\s*\\{").matcher(sourceCode);
//		matcher.find();
//		System.out.println(matcher.group(1));
//	}
//}
