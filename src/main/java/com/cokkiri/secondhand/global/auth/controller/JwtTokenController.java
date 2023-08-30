package com.cokkiri.secondhand.global.auth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cokkiri.secondhand.global.auth.dto.request.RefreshTokenRequest;
import com.cokkiri.secondhand.global.auth.dto.response.JwtTokenResponse;
import com.cokkiri.secondhand.global.auth.infrastructure.JwtAuthHttpResponseManager;
import com.cokkiri.secondhand.global.auth.service.JwtTokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class JwtTokenController {

	private final JwtAuthHttpResponseManager jwtAuthHttpResponseManager;
	private final JwtTokenService jwtTokenService;

	@PostMapping("/api/reissue-access-token")
	public ResponseEntity<JwtTokenResponse> reissueAccessToken(
		@RequestBody RefreshTokenRequest refreshTokenRequest,
		HttpServletResponse response) {

		JwtTokenResponse jwtTokenResponse = jwtTokenService.reissueJwtToken(refreshTokenRequest.getRefreshToken());

		jwtAuthHttpResponseManager.setAuthHttpResponse(response, jwtTokenResponse);

		return ResponseEntity.ok(jwtTokenResponse);
	}
}
