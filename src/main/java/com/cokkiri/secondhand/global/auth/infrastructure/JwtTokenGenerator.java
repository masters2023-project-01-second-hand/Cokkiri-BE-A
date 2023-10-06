package com.cokkiri.secondhand.global.auth.infrastructure;

import java.security.Key;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cokkiri.secondhand.global.auth.dto.response.JwtTokenResponse;
import com.cokkiri.secondhand.global.auth.entity.JwtAccessToken;
import com.cokkiri.secondhand.global.auth.entity.JwtRefreshToken;
import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenGenerator  {

	private static final ZoneId DEFAULT_TIME_ZONE = ZoneId.of("UTC");

	private final String secret;
	private final Long accessTokenExpirationTime;
	private final Long refreshTokenExpirationTime;
	private final String accessSubject;
	private final String refreshSubject;
	private Key secretKey;

	public JwtTokenGenerator(
		MemoryJwtRepository memoryJwtRepository,
		@Value("${jwt.secret}") String secret,
		@Value("${jwt.access.expiration-time-in-seconds}") Long accessTokenExpirationTime,
		@Value("${jwt.refresh.expiration-time-in-seconds}") Long refreshTokenExpirationTime,
		@Value("${jwt.access.subject}") String accessSubject,
		@Value("${jwt.access.subject}") String refreshSubject) {

		this.secret = secret;
		this.accessTokenExpirationTime = accessTokenExpirationTime;
		this.refreshTokenExpirationTime = refreshTokenExpirationTime;
		this.accessSubject = accessSubject;
		this.refreshSubject = refreshSubject;
	}

	@PostConstruct
	public void init() throws Exception {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public JwtTokenResponse createJwtTokenResponse(UserInfoForJwt user) {
		JwtAccessToken accessToken = createAccessToken(user);
		JwtRefreshToken refreshToken = createRefreshToken();

		return new JwtTokenResponse(
			accessToken.getAccessToken(),
			accessToken.getExpirationDateTime(),
			refreshToken.getRefreshToken(),
			refreshToken.getExpirationDateTime()
		);
	}

	public UserInfoForJwt getUserInfoForJwtBy(String token) {

		String errorMessage;

		try {
			Claims claims = Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody();

			return UserInfoForJwt.generateUserInfo(
				Long.valueOf((String) claims.get("id")),
				(String) claims.get("userType")
			);
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			errorMessage = "잘못된 JWT 서명입니다.";
		} catch (ExpiredJwtException e) {
			errorMessage = "만료된 JWT 토큰입니다.";
		} catch (UnsupportedJwtException e) {
			errorMessage = "지원되지 않는 JWT 토큰입니다.";
		} catch (IllegalArgumentException e) {
			errorMessage = "JWT 토큰이 잘못되었습니다.";
		}

		// 현재 프론트단과 refresh token 로직을 맞춰보지 않아, 토큰 검증 실패시 에러를 발생시키는 대신 게스트 회원을 반환 하도록 임시 변경
		// throw new IllegalJwtTokenException(errorMessage);
		log.info(errorMessage);
		return UserInfoForJwt.generateGuestUserInfo();
	}

	private JwtAccessToken createAccessToken(UserInfoForJwt user) {
		ZonedDateTime expirationDateTime = getAccessExpirationDateTime();
		return JwtAccessToken.from(
			createToken(accessSubject, user.generateClaims(), expirationDateTime),
			expirationDateTime
		);
	}

	private JwtRefreshToken createRefreshToken() {
		ZonedDateTime expirationDateTime = getRefreshExpirationDateTime();
		return JwtRefreshToken.from(
			createToken(refreshSubject, Map.of(), expirationDateTime),
			expirationDateTime
		);
	}

	private String createToken(String subject, Map<String, String> claims, ZonedDateTime expirationDateTime) {
		return Jwts.builder()
			.setSubject(subject)
			.setClaims(claims)
			.setIssuedAt(new Date())
			.setExpiration(Date.from(expirationDateTime.toInstant()))
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	private ZonedDateTime getAccessExpirationDateTime() {
		return ZonedDateTime.now(DEFAULT_TIME_ZONE)
			.plusSeconds(accessTokenExpirationTime);
	}

	private ZonedDateTime getRefreshExpirationDateTime() {
		return ZonedDateTime.now(DEFAULT_TIME_ZONE)
			.plusSeconds(refreshTokenExpirationTime);
	}

}
