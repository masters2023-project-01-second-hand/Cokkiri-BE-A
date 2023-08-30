package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;

public class NotFoundUserByUsernameException extends CustomException {

	public NotFoundUserByUsernameException(String nickname) {
		super(nickname + "은 존재하지 않는 회원입니다.");
	}
}
