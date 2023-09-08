package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class EmptyMyLocationListException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "내 동네 정보 목록이 비어있습니다.";

	public EmptyMyLocationListException() {
		super(
			ERROR_MESSAGE_FORMAT,
			CustomExceptionType.EMPTY_MY_LOCATION_LIST_EXCEPTION);
	}
}
