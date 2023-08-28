package com.cokkiri.secondhand.global.auth.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cokkiri.secondhand.global.auth.dto.response.JwtTokenResponse;
import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.auth.infrastructure.JwtAuthHttpResponseManager;
import com.cokkiri.secondhand.global.auth.service.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtTokenService jwtTokenService;
	private final JwtAuthHttpResponseManager jwtAuthHttpResponseManager;
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws
		IOException {

		DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

		JwtTokenResponse jwtTokenResponse = jwtTokenService.issueTokens(UserInfoForJwt.from(defaultOAuth2User));

		sendJwtTokenResponse(response, jwtTokenResponse);
	}

	private void sendJwtTokenResponse(HttpServletResponse response, JwtTokenResponse jwtTokenResponse) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.OK.value());

		jwtAuthHttpResponseManager.setAuthHttpResponse(response, jwtTokenResponse);

		response.getWriter().write(
			objectMapper.writeValueAsString(
				jwtTokenResponse
			)
		);
	}

}

