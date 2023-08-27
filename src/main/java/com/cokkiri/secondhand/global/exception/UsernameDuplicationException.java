package com.cokkiri.secondhand.global.exception;

public class UsernameDuplicationException extends RuntimeException {

	public UsernameDuplicationException(String username) {
		super(username + "은 이미 사용 중인 아이디 입니다.");
	}
}
