package com.cokkiri.secondhand.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomExceptionHttpStatusCodeFactory {

	public HttpStatus getHttpStatusCodeBy(CustomExceptionType type) {
		switch (type) {
			case ILLEGAL_JWT_TOKEN_EXCEPTION:
			case NOT_EXIST_ACCESS_TOKEN_EXCEPTION:
			case NOT_EXIST_REFRESH_TOKEN_EXCEPTION:
			case LOGIN_FAILURE_EXCEPTION:
				return HttpStatus.UNAUTHORIZED;	// 401
			case NOT_FOUND_LOCATION_EXCEPTION:
				return HttpStatus.NOT_FOUND;	// 404
			case NICKNAME_DUPLICATION_EXCEPTION:
			case USERNAME_DUPLICATION_EXCEPTION:
				return HttpStatus.CONFLICT;		// 409
			case ILLEGAL_STATUS_EXCEPTION:
			default:
				return HttpStatus.BAD_REQUEST;	// 400
		}
	}
}
