package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;

public class IllegalJwtTokenException extends CustomException {
	public IllegalJwtTokenException(String message) {
		super(message);
	}
}
