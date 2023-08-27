package com.cokkiri.secondhand.global.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtTokenResponse {

	private String accessToken;
	private String refreshToken;
}
