package com.cokkiri.secondhand.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final String CONSOLE_MESSAGE = "\n\n /)___/)\n(„• ֊ •„) [Exception occurred!]\n O₊˚₊˚₊O";
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private final CustomExceptionHttpStatusCodeFactory httpStatusCodeFactory;

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorMessageResponse> handleCustomException(CustomException e) {
		LOGGER.error(CONSOLE_MESSAGE, e);

		HttpStatus httpStatus
			= httpStatusCodeFactory.getHttpStatusCodeBy(e.getExceptionType());

		return ResponseEntity
			.status(httpStatus)
			.body(new ErrorMessageResponse(e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessageResponse> handleException(Exception e) {
		LOGGER.error(CONSOLE_MESSAGE, e);

		return ResponseEntity
			.internalServerError()
			.body(new ErrorMessageResponse(e.getMessage()));
	}

}
