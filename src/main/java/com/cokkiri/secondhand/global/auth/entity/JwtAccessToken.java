package com.cokkiri.secondhand.global.auth.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
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

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expirationDateTime);
	}

	public boolean equalsAccessToken(String accessToken) {
		return this.accessToken.equals(accessToken);
	}
}
