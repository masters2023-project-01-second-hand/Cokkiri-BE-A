package com.cokkiri.secondhand.global.auth.entity;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtAccessToken {

	private String accessToken;
	private LocalDateTime expirationDateTime;

	public static JwtAccessToken from(String accessToken, LocalDateTime expirationDateTime) {
		return new JwtAccessToken(
			accessToken,
			expirationDateTime
		);
	}
}
