package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;

public class LoginFailureException extends CustomException {

	public LoginFailureException() {
		super("아이디 또는 비밀번호가 일치하지 않습니다.");
	}
}
