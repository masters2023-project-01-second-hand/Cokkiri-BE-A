package com.cokkiri.secondhand.global.exception;

public class NotExistAccessTokenException extends RuntimeException {
	public NotExistAccessTokenException() {
		super("Access 토큰이 존재하지 않습니다.");
	}
}
