package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class FileUploadFailureException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "파일 업로드에 실패했습니다.";

	public FileUploadFailureException() {
		super(
			ERROR_MESSAGE_FORMAT,
			CustomExceptionType.FILE_UPLOAD_FAILURE_EXCEPTION);
	}
}
