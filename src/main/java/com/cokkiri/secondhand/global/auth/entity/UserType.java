package com.cokkiri.secondhand.global.auth.entity;

public enum UserType {
	NONE, GENERAL, OAUTH;

	public static UserType getUserTypeBy(String name) {
		for(UserType type : UserType.values()) {
			if (type.name().equals(name)) {
				return type;
			}
		}
		return UserType.NONE;
	}
}
