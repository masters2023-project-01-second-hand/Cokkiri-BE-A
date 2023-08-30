package com.cokkiri.secondhand.global.exception;

public class NicknameDuplicationException extends RuntimeException {

	public NicknameDuplicationException(String nickname) {
		super(nickname + "은 이미 사용 중인 아이디 입니다.");
	}
}
