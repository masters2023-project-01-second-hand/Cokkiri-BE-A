package com.cokkiri.secondhand.global.auth.domain;

import java.util.Map;

import com.cokkiri.secondhand.user.entity.GeneralUser;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoForJwt {

	private String id;
	private UserType userType;

	public UserInfoForJwt(String id, String userTypename) {
		this.id =id;
		this.userType = UserType.getUserTypeBy(userTypename);
	}

	public static UserInfoForJwt from(GeneralUser user) {
		return new UserInfoForJwt(
			String.valueOf(user.getId()),
			UserType.GENERAL
		);
	}

	public Map<String, String> generateClaims() {
		return Map.of("id", id, "userType", userType.name());
	}
}
