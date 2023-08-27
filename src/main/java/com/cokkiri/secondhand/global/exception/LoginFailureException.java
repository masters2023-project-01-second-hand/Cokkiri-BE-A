package com.cokkiri.secondhand.global.exception;

public class LoginFailureException extends RuntimeException {

	public LoginFailureException() {
		super("아이디 또는 비밀번호가 일치하지 않습니다.");
	}
}
