package com.cokkiri.secondhand.global.auth.infrastructure;

import java.io.IOException;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cokkiri.secondhand.global.auth.dto.response.JwtTokenResponse;
import com.cokkiri.secondhand.global.exception.ErrorMessageResponse;
import com.cokkiri.secondhand.global.exception.list.NotExistAccessTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtAuthHttpResponseManager {

	private final String accessHttpHeader = HttpHeaders.AUTHORIZATION;

	private final String authorizationType;
	private final String refreshHttpHeader;

	public JwtAuthHttpResponseManager(
		@Value("${jwt.authorization-type}") String authorizationType,
		@Value("${jwt.refresh.http-header}") String refreshHttpHeader) {

		this.authorizationType = authorizationType + " ";
		this.refreshHttpHeader = refreshHttpHeader;
	}

	public void setAuthHttpResponse(HttpServletResponse response, JwtTokenResponse jwtTokenResponse) {
		setAccessTokenHeader(response, jwtTokenResponse.getAccessToken());
		setRefreshTokenHeader(response, jwtTokenResponse.getRefreshToken());
	}

	public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
		response.setHeader(accessHttpHeader, authorizationType + accessToken);
	}

	public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
		response.setHeader(refreshHttpHeader, authorizationType + refreshToken);
	}

	public boolean isContainAccessToken(HttpServletRequest request) {
		String authorization = request.getHeader(accessHttpHeader);
		return authorization != null && authorization.startsWith(authorizationType);
	}

	public String getAccessToken(HttpServletRequest request) {
		String authorization = request.getHeader(accessHttpHeader);
		return authorization.replace(authorizationType, "");
	}

	public void sendNotExistAccessTokenException(ServletResponse response, ObjectMapper objectMapper) throws IOException {
		sendErrorResponseEntity(response, objectMapper, new NotExistAccessTokenException());
	}

	public void sendErrorResponseEntity(ServletResponse response, ObjectMapper objectMapper, RuntimeException e) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		((HttpServletResponse)response).setStatus(HttpStatus.UNAUTHORIZED.value());

		response.getWriter().write(
			objectMapper.writeValueAsString(
				ResponseEntity
					.badRequest()
					.body(new ErrorMessageResponse(e.getMessage()))
			)
		);
	}
}
