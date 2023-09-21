package com.cokkiri.secondhand.global.exception.list;

import org.springframework.web.multipart.MultipartFile;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class IllegalFileStateException extends CustomException {

	private static final String NULL_ERROR_MESSAGE_FORMAT = "[file is null]유효하지 않은 파일입니다.";

	private static final String ERROR_MESSAGE_FORMAT = "유효하지 않은 파일입니다.\n\t"
		+ "fileOriginalName: %s\n\t"
		+ "fileSize: %d";

	public IllegalFileStateException(MultipartFile multipartFile) {
		super(
			String.format(ERROR_MESSAGE_FORMAT, multipartFile.getOriginalFilename(), multipartFile.getSize()),
			CustomExceptionType.ILLEGAL_FILE_STATE_EXCEPTION);
	}
}
