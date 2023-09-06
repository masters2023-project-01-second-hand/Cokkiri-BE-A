package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class LimitExceededMyLocationException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "내 동네를 더 이상 추가할 수 없습니다.";

	public LimitExceededMyLocationException() {
		super(
			ERROR_MESSAGE_FORMAT,
			CustomExceptionType.LIMIT_EXCEEDED_MY_LOCATION_EXCEPTION);
	}
}
