package com.cokkiri.secondhand.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final CustomExceptionType exceptionType;

	public CustomException(String message, CustomExceptionType exceptionType) {
		super(message);
		this.exceptionType = exceptionType;
	}
}
