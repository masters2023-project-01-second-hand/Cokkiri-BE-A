package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;

public class NicknameDuplicationException extends CustomException {

	public NicknameDuplicationException(String nickname) {
		super(nickname + "은 이미 사용 중인 아이디 입니다.");
	}
}
