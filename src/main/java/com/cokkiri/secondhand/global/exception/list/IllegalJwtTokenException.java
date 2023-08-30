package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class IllegalJwtTokenException extends CustomException {

	public IllegalJwtTokenException(String message) {
		super(message, CustomExceptionType.ILLEGAL_JWT_TOKEN_EXCEPTION);
	}
}
