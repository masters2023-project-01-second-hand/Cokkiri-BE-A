package com.cokkiri.secondhand.global.auth.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshTokenRequest {

	private String refreshToken;
}