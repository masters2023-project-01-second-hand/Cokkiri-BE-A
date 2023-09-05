package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class DuplicateMyLocationException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "[%s]은/는 이미 등록된 지역 입니다.";

	public DuplicateMyLocationException(String locationName) {
		super(
			String.format(ERROR_MESSAGE_FORMAT, locationName),
			CustomExceptionType.DUPLICATE_MY_LOCATION_EXCEPTION);
	}
}
