package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;

public class NotExistRefreshTokenException extends CustomException {
	public NotExistRefreshTokenException() {
		super("Refresh 토큰이 존재하지 않습니다.");
	}
}
