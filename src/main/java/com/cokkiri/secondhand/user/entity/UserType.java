package com.cokkiri.secondhand.user.entity;

public enum UserType {
	GUEST, GENERAL, GITHUB;

	public static UserType getUserTypeBy(String name) {
		for(UserType type : UserType.values()) {
			if (type.name().equals(name)) {
				return type;
			}
		}
		return UserType.GUEST;
	}
}
