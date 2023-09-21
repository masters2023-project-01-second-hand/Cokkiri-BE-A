package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class NotFoundCategoryException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "[id=%d]에 해당하는 카테고리가 존재하지 않습니다.";

	public NotFoundCategoryException(Long id) {
		super(
			String.format(ERROR_MESSAGE_FORMAT, id),
			CustomExceptionType.NOT_FOUND_CATEGORY_EXCEPTION);
	}
}
