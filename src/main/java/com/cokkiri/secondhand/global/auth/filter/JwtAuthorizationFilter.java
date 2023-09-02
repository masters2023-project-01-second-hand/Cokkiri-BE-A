package com.cokkiri.secondhand.global.auth.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.auth.infrastructure.JwtAuthHttpResponseManager;
import com.cokkiri.secondhand.global.auth.infrastructure.JwtTokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Value("${jwt.whitelist}")
	private String[] whiteListUrls;

	private final JwtAuthHttpResponseManager jwtAuthHttpResponseManager;
	private final JwtTokenGenerator jwtTokenGenerator;
	private final ObjectMapper objectMapper;

	private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

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

		if (!jwtAuthHttpResponseManager.isContainAccessToken(httpServletRequest)) {
			jwtAuthHttpResponseManager.sendNotExistAccessTokenException(response, objectMapper);
			return;
		}

		try {
			String token = jwtAuthHttpResponseManager.getAccessToken(httpServletRequest);
			UserInfoForJwt userInfoForJwt = jwtTokenGenerator.getUserForJwtBy(token);
			SecurityContextHolder.getContext().setAuthentication(getAuthentication(userInfoForJwt));

			request.setAttribute("userInfoForJwt", userInfoForJwt);

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			jwtAuthHttpResponseManager.sendErrorResponseEntity(response, objectMapper, new RuntimeException(e));
		}
	}

	private boolean whiteListCheck(String uri) {
		return PatternMatchUtils.simpleMatch(whiteListUrls, uri);
	}

	public Authentication getAuthentication(UserInfoForJwt userInfoForJwt) {

		UserDetails userDetailsUser = User.builder()
			.username(userInfoForJwt.getId())
			.password(UUID.randomUUID().toString())
			.roles(userInfoForJwt.getUserType().name())
			.build();

		return new UsernamePasswordAuthenticationToken(
			userDetailsUser,
			null,
			authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities())
		);
	}
}
