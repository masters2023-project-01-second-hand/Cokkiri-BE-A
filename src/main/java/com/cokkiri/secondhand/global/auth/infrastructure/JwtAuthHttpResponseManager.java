package com.cokkiri.secondhand.global.auth.infrastructure;

import java.io.IOException;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cokkiri.secondhand.global.auth.dto.response.JwtTokenResponse;
import com.cokkiri.secondhand.global.exception.ErrorMessageResponse;
import com.cokkiri.secondhand.global.exception.NotExistAccessTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtAuthHttpResponseManager {

	private final String AUTHORIZATION_TYPE;
	private final String ACCESS_HTTP_HEADER;
	private final String REFRESH_HTTP_HEADER;

	public JwtAuthHttpResponseManager(
		@Value("${jwt.authorization-type}") String AUTHORIZATION_TYPE,
		@Value("${jwt.access.http-header}") String ACCESS_HTTP_HEADER,
		@Value("${jwt.refresh.http-header}") String REFRESH_HTTP_HEADER) {

		this.AUTHORIZATION_TYPE = AUTHORIZATION_TYPE + " ";
		this.ACCESS_HTTP_HEADER = ACCESS_HTTP_HEADER;
		this.REFRESH_HTTP_HEADER = REFRESH_HTTP_HEADER;
	}

	public JwtTokenResponse setAuthHttpResponse(HttpServletResponse response, JwtTokenResponse jwtTokenResponse) {
		setAccessTokenHeader(response, jwtTokenResponse.getAccessToken());
		setRefreshTokenHeader(response, jwtTokenResponse.getRefreshToken());
		return jwtTokenResponse;
	}

	public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
		response.setHeader(ACCESS_HTTP_HEADER, AUTHORIZATION_TYPE + accessToken);
	}

	public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
		response.setHeader(REFRESH_HTTP_HEADER, AUTHORIZATION_TYPE + refreshToken);
	}

	public boolean isContainAccessToken(HttpServletRequest request) {
		String authorization = request.getHeader(ACCESS_HTTP_HEADER);
		return authorization != null && authorization.startsWith(AUTHORIZATION_TYPE);
	}

	public String getAccessToken(HttpServletRequest request) {
		String authorization = request.getHeader(ACCESS_HTTP_HEADER);
		return authorization.replace(ACCESS_HTTP_HEADER, "");
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
