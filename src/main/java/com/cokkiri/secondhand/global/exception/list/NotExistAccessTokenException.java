package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;

public class NotExistAccessTokenException extends CustomException {
	public NotExistAccessTokenException() {
		super("Access 토큰이 존재하지 않습니다.");
	}
}
