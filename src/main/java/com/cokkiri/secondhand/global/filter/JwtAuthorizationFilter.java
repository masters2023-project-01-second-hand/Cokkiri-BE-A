package com.cokkiri.secondhand.global.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cokkiri.secondhand.global.auth.domain.UserInfoForJwt;
import com.cokkiri.secondhand.global.auth.jwt.JwtTokenGenerator;
import com.cokkiri.secondhand.global.exception.ErrorMessageResponse;
import com.cokkiri.secondhand.global.exception.NotExistAccessTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Value("${jwt.whitelist}")
	private String[] whiteListUrls;

	@Value("${jwt.access.http-header}")
	private String accessHttpHeader;

	private final JwtTokenGenerator jwtTokenGenerator;
	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		HttpServletRequest httpServletRequest = (HttpServletRequest)request;

		if (httpServletRequest.getMethod().equals("OPTIONS")) {
			return;
		}

		if (whiteListCheck(httpServletRequest.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		if (!isContainAccessToken(httpServletRequest)) {
			sendNotExistAccessTokenException(response);
			return;
		}

		try {
			String token = getAccessToken(httpServletRequest);
			UserInfoForJwt userInfoForJwt = jwtTokenGenerator.getUserForJwtBy(token);

			request.setAttribute("memberId", userInfoForJwt);

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			sendErrorResponseEntity(response, new RuntimeException(e));
		}
	}

	private boolean whiteListCheck(String uri) {
		return PatternMatchUtils.simpleMatch(whiteListUrls, uri);
	}

	private boolean isContainAccessToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		return authorization != null && authorization.startsWith("Bearer ");
	}

	private String getAccessToken(HttpServletRequest request) {
		String authorization = request.getHeader(accessHttpHeader);
		return authorization.substring(7).replace("\"", "");
	}

	private void sendNotExistAccessTokenException(ServletResponse response) throws IOException {
		sendErrorResponseEntity(response, new NotExistAccessTokenException());
	}

	private void sendErrorResponseEntity(ServletResponse response, RuntimeException e) throws IOException {
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
