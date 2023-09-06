package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class NotFoundMyLocationException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "[id=%d]에 해당하는 내 동네 정보를 찾을 수 없습니다.";

	public NotFoundMyLocationException(Long id) {
		super(
			String.format(ERROR_MESSAGE_FORMAT, id),
			CustomExceptionType.NOT_FOUND_MY_LOCATION_EXCEPTION);
	}
}
