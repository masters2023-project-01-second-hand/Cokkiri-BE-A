package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class NotFoundLocationException extends CustomException {

	public NotFoundLocationException(String message) {
		super(
			String.format("[%s]는 존재하지 않는 위치 정보입니다.", message),
			CustomExceptionType.NOT_FOUND_LOCATION_EXCEPTION);
	}
}
