package com.cokkiri.secondhand.global.exception;

public class NotExistLocationException  extends RuntimeException {
	public NotExistLocationException(String message) {
		super(String.format("[%s]는 존재하지 않는 위치 정보입니다.", message));
	}
}
