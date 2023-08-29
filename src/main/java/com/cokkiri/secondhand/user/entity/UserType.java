package com.cokkiri.secondhand.user.entity;

public enum UserType {
	NONE, GENERAL, GITHUB;

	public static UserType getUserTypeBy(String name) {
		for(UserType type : UserType.values()) {
			if (type.name().equals(name)) {
				return type;
			}
		}
		return UserType.NONE;
	}
}
