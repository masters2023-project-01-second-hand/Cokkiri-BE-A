package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class NotExistFileException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "파일이 존재하지 않습니다.";

	public NotExistFileException() {
		super(
			String.format(ERROR_MESSAGE_FORMAT),
			CustomExceptionType.NOT_EXIST_FILE_EXCEPTION);
	}
}
