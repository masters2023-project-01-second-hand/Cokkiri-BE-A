package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class NicknameDuplicationException extends CustomException {

	private static final String ERROR_MESSAGE_FORMAT = "[%s]은/는 이미 사용 중인 닉네임 입니다.";

	public NicknameDuplicationException(String nickname) {
		super(
			String.format(ERROR_MESSAGE_FORMAT, nickname),
			CustomExceptionType.NICKNAME_DUPLICATION_EXCEPTION);
	}
}
