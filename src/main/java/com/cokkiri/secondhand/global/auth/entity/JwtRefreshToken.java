package com.cokkiri.secondhand.global.auth.entity;

import java.time.ZonedDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtRefreshToken {

	private String refreshToken;
	private ZonedDateTime expirationDateTime;

	public static JwtRefreshToken from(String refreshToken, ZonedDateTime expirationDateTime) {
		return new JwtRefreshToken(
			refreshToken,
			expirationDateTime
		);
	}

	public boolean isExpired() {
		return ZonedDateTime.now().isAfter(expirationDateTime);
	}

	public boolean equalsRefreshToken(String refreshToken) {
		return this.refreshToken.equals(refreshToken);
	}
}
