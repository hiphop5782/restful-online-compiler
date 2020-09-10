package com.hacademy.runner.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class JavaResultVO {
	private String className;
	private String code;
	private String result;
	private LocalDateTime compileTime;
	private LocalDateTime executeTime;
	private long compileElapsed;
	private long executeElapsed;
}
