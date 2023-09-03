package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class LoginFailureException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "아이디 또는 비밀번호가 일치하지 않습니다.";

	public LoginFailureException() {
		super(
			ERROR_MESSAGE_FORMAT,
			CustomExceptionType.LOGIN_FAILURE_EXCEPTION);
	}
}
