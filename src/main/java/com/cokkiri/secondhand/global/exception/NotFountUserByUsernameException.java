package com.cokkiri.secondhand.global.exception;

public class NotFountUserByUsernameException extends RuntimeException {

	public NotFountUserByUsernameException(String nickname) {
		super(nickname + "은 존재하지 않는 회원입니다.");
	}
}
