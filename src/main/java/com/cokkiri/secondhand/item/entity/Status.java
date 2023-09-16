package com.cokkiri.secondhand.item.entity;

import com.cokkiri.secondhand.global.exception.list.IllegalStatusException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
	SALE(1L, "판매중"), RESERVATION(2L, "예약중"), SOLD_OUT(3L, "판매완료");

	private final Long id;
	private final String name;

	public static Status parseBy(String name) {
		switch (name) {
			case "판매중": return Status.SALE;
			case "예약중": return Status.RESERVATION;
			case "판매완료": return Status.SOLD_OUT;
			default:
				throw new IllegalStatusException(name);
		}
	}

	public static Status determineStatusIsSold(boolean isSold) {
		return isSold? Status.SOLD_OUT : Status.SALE;
	}
}
