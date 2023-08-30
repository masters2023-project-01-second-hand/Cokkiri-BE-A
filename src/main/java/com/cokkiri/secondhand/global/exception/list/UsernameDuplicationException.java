package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class UsernameDuplicationException extends CustomException {

	public UsernameDuplicationException(String username) {
		super(
			String.format("[%s]은/는 이미 사용 중인 아이디 입니다.", username),
			CustomExceptionType.USER_NAME_DUPLICATION_EXCEPTION);
	}
}
