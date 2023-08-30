package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;
import com.cokkiri.secondhand.global.exception.CustomExceptionType;

public class NicknameDuplicationException extends CustomException {

	public NicknameDuplicationException(String nickname) {
		super(
			String.format("[%s]은/는 이미 사용 중인 닉네임 입니다.", nickname),
			CustomExceptionType.NICKNAME_DUPLICATION_EXCEPTION);
	}
}
