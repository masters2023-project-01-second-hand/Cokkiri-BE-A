package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class MyLocationDeletionNotAllowedException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "내 동네를 제거할 수 없습니다.";

	public MyLocationDeletionNotAllowedException() {
		super(
			ERROR_MESSAGE_FORMAT,
			CustomExceptionType.MY_LOCATION_DELETION_NOT_ALLOWED_EXCEPTION);
	}

}
