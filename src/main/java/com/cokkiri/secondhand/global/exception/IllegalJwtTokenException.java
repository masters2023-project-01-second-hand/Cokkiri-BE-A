package com.cokkiri.secondhand.global.exception;

public class IllegalJwtTokenException extends RuntimeException {
	public IllegalJwtTokenException(String message) {
		super(message);
	}
}
