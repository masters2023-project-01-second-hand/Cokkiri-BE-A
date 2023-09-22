package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class FavoriteCountFailureException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "조회수 증감에 실패하였습니다.";

	public FavoriteCountFailureException() {
		super(
			ERROR_MESSAGE_FORMAT,
			CustomExceptionType.FAVORITE_COUNT_FAILURE_EXCEPTION);
	}
}
