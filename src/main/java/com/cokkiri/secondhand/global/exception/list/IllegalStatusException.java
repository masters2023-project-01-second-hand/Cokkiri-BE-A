package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class IllegalStatusException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "[%s]은/는 유효한 Status값이 아닙니다.";

	public IllegalStatusException(String name) {
		super(
			String.format(ERROR_MESSAGE_FORMAT, name),
			CustomExceptionType.ILLEGAL_STATUS_EXCEPTION);
	}
}
