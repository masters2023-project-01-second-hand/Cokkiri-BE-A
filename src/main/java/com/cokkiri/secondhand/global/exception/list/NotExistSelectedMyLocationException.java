package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class NotExistSelectedMyLocationException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "내 동네 목록에서 선택된 동네가 없습니다.";

	public NotExistSelectedMyLocationException() {
		super(
			ERROR_MESSAGE_FORMAT,
			CustomExceptionType.NOT_EXIST_SELECTED_MY_LOCATION_EXCEPTION);
	}
}
