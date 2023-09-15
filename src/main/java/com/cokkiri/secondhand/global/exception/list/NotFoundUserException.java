package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class NotFoundUserException extends CustomException {

	private static final String ID_ERROR_MESSAGE_FORMAT = "[id=%d]에 해당하는 유저가 존재하지 않습니다.";
	private static final String NICKNAME_ERROR_MESSAGE_FORMAT = "[nickname=%s]에 해당하는 유저가 존재하지 않습니다.";

	public NotFoundUserException(Long id) {
		super(
			String.format(ID_ERROR_MESSAGE_FORMAT, id),
			CustomExceptionType.NOT_FOUND_USER_EXCEPTION);
	}

	public NotFoundUserException(String nickname) {
		super(
			String.format(NICKNAME_ERROR_MESSAGE_FORMAT, nickname),
			CustomExceptionType.NOT_FOUND_USER_EXCEPTION);
	}
}
