package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class NotFoundLocationException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "[%s]은/는 존재하지 않는 위치 정보입니다.";

	public NotFoundLocationException(String message) {
		super(
			String.format(ERROR_MESSAGE_FORMAT, message),
			CustomExceptionType.NOT_FOUND_LOCATION_EXCEPTION);
	}
}
