package com.hacademy.runner.service;

import org.springframework.stereotype.Service;

@Service
public class GenerateStringServiceImpl implements GenerateStringService{

	@Override
	public String generateTestClassName(int length) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Test");
		for(int i=0; i < length; i++) {
			buffer.append(generateRandomNumber());
		}
		return buffer.toString();
	}
	
	private int generateRandomNumber() {
		return (int)(Math.random() * 10);
	}
	
}
