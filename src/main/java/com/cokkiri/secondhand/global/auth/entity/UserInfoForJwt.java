package com.cokkiri.secondhand.global.auth.entity;

import java.util.Map;
import java.util.Objects;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.cokkiri.secondhand.user.entity.GeneralUser;
import com.cokkiri.secondhand.user.entity.UserType;

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

	public static UserInfoForJwt from(DefaultOAuth2User defaultOAuth2User) {
		Map<String, Object> attributes = defaultOAuth2User.getAttributes();

		return new UserInfoForJwt(
			String.valueOf(attributes.get("id")),
			UserType.GITHUB
		);
	}

	public Map<String, String> generateClaims() {
		return Map.of("id", id, "userType", userType.name());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserInfoForJwt that = (UserInfoForJwt)o;
		return Objects.equals(id, that.id) && userType == that.userType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userType);
	}
}
