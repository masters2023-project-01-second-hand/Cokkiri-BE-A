package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class NotFoundItemException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "[id=%d]에 해당하는 아이템이 존재하지 않습니다.";

	public NotFoundItemException(Long id) {
		super(
			String.format(ERROR_MESSAGE_FORMAT, id),
			CustomExceptionType.NOT_FOUND_ITEM_EXCEPTION);
	}
}
