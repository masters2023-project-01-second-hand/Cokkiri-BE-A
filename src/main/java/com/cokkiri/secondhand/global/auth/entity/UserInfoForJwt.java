package com.cokkiri.secondhand.global.auth.entity;

import java.util.Map;
import java.util.Objects;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.cokkiri.secondhand.user.entity.GeneralUser;
import com.cokkiri.secondhand.user.entity.UserType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserInfoForJwt {

	private Long userId;
	private UserType userType;

	public static UserInfoForJwt from(GeneralUser user) {
		return new UserInfoForJwt(
			user.getId(),
			UserType.GENERAL
		);
	}

	public static UserInfoForJwt generateUserInfo(Long userId, String userTypename) {
		return new UserInfoForJwt(userId, UserType.getUserTypeBy(userTypename));
	}

	public static UserInfoForJwt generateUserInfo(Long userId, UserType userType) {
		return new UserInfoForJwt(userId, userType);
	}

	public static UserInfoForJwt generateGuestUserInfo() {
		return new UserInfoForJwt(0L, UserType.GUEST);
	}

	public static UserInfoForJwt from(DefaultOAuth2User defaultOAuth2User) {
		Map<String, Object> attributes = defaultOAuth2User.getAttributes();

		return new UserInfoForJwt(
			(Long)attributes.get("id"),
			UserType.GITHUB
		);
	}

	public boolean isGuest() {
		return this.userType == UserType.GUEST;
	}

	public Map<String, String> generateClaims() {
		return Map.of("id", String.valueOf(this.userId), "userType", userType.name());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserInfoForJwt that = (UserInfoForJwt)o;
		return Objects.equals(userId, that.userId) && userType == that.userType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, userType);
	}
}
