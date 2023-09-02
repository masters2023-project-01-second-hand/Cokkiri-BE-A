package com.cokkiri.secondhand.global.auth.service;

import org.springframework.stereotype.Service;

import com.cokkiri.secondhand.global.auth.dto.response.JwtTokenResponse;
import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.auth.infrastructure.JwtTokenGenerator;
import com.cokkiri.secondhand.global.auth.infrastructure.MemoryJwtRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtTokenService {

	private final JwtTokenGenerator jwtTokenGenerator;
	private final MemoryJwtRepository memoryJwtRepository;

	public JwtTokenResponse reissueJwtToken(String refreshToken) {
		UserInfoForJwt userInfoForJwt = memoryJwtRepository.getUserInfoForJwt(refreshToken);
		return issueTokens(userInfoForJwt);
	}

	public JwtTokenResponse issueTokens(UserInfoForJwt userInfoForJwt) {
		JwtTokenResponse jwtTokenResponse = jwtTokenGenerator.createJwtTokenResponse(userInfoForJwt);
		memoryJwtRepository.save(userInfoForJwt, jwtTokenResponse.toRefreshToken());
		return jwtTokenResponse;
	}
}
