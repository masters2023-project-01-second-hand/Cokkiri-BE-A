package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class NotExistAccessTokenException extends CustomException {

	public NotExistAccessTokenException() {
		super(
			"Access 토큰이 존재하지 않습니다.",
			CustomExceptionType.NOT_EXIST_ACCESS_TOKEN_EXCEPTION);
	}
}
