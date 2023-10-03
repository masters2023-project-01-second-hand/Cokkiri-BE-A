package com.cokkiri.secondhand.global.auth.dto.response;

import lombok.Getter;

@Getter
public class LoginResponse {

	private JwtTokenResponse jwtTokenResponse;
	private String nickname;
	private String profileImageUrl;

	public LoginResponse(JwtTokenResponse jwtTokenResponse, String nickname, String profileImageUrl) {
		this.jwtTokenResponse = jwtTokenResponse;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}

	public static LoginResponse of(JwtTokenResponse jwtTokenResponse, String nickname, String profileImageUrl) {
		return new LoginResponse(jwtTokenResponse, nickname, profileImageUrl);
	}
}
