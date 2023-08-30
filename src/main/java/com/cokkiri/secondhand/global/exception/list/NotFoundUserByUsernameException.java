package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class NotFoundUserByUsernameException extends CustomException {

	public NotFoundUserByUsernameException(String nickname) {
		super(
			nickname + "은 존재하지 않는 회원입니다.",
			CustomExceptionType.NOT_FOUND_USER_BY_USERNAME_EXCEPTION);
	}
}
