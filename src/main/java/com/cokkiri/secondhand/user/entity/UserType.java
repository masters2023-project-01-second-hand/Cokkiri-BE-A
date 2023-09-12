package com.cokkiri.secondhand.user.entity;

import java.util.Arrays;

public enum UserType {
	GUEST, GENERAL, GITHUB;

	public static UserType getUserTypeBy(String name) {

		return Arrays.stream(UserType.values())
			.filter(type -> type.name().equals(name))
			.findFirst()
			.orElse(UserType.GUEST);
	}
}
