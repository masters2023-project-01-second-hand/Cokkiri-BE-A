package com.cokkiri.secondhand.global.exception;

public class NotExistRefreshTokenException extends RuntimeException {
	public NotExistRefreshTokenException() {
		super("Refresh 토큰이 존재하지 않습니다.");
	}
}
