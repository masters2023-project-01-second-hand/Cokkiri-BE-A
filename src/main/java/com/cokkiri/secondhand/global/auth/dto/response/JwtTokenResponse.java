package com.cokkiri.secondhand.global.auth.dto.response;

import java.time.ZonedDateTime;

import com.cokkiri.secondhand.global.auth.entity.JwtRefreshToken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtTokenResponse {

	private String accessToken;
	private ZonedDateTime expirationAccessTokenDateTime;
	private String refreshToken;
	private ZonedDateTime expirationRefreshTokenDateTime;

	public JwtRefreshToken toRefreshToken() {
		return JwtRefreshToken.from(
			refreshToken,
			expirationRefreshTokenDateTime
		);
	}


}
