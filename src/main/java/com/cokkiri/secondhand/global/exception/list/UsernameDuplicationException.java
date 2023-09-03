package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class UsernameDuplicationException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "[%s]은/는 이미 사용 중인 아이디 입니다.";

	public UsernameDuplicationException(String username) {
		super(
			String.format(ERROR_MESSAGE_FORMAT, username),
			CustomExceptionType.USERNAME_DUPLICATION_EXCEPTION);
	}
}
