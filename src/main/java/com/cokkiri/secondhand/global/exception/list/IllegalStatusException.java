package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class IllegalStatusException extends CustomException {

	public IllegalStatusException(String name) {
		super(
			String.format("[%s]은 유효한 Status값이 아닙니다.", name),
			CustomExceptionType.ILLEGAL_STATUS_EXCEPTION);
	}
}
