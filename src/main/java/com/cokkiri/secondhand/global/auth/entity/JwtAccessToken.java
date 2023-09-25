package com.cokkiri.secondhand.global.auth.entity;

import java.time.ZonedDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtAccessToken {

	private String accessToken;
	private ZonedDateTime expirationDateTime;

	public static JwtAccessToken from(String accessToken, ZonedDateTime expirationDateTime) {
		return new JwtAccessToken(
			accessToken,
			expirationDateTime
		);
	}
}
