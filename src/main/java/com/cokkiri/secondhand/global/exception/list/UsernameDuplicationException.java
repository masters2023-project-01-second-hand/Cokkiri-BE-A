package com.cokkiri.secondhand.global.exception.list;

import com.cokkiri.secondhand.global.exception.CustomException;

public class UsernameDuplicationException extends CustomException {

	public UsernameDuplicationException(String username) {
		super(username + "은 이미 사용 중인 아이디 입니다.");
	}
}
