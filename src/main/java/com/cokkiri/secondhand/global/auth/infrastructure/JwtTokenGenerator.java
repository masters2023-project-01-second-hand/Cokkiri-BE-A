package com.cokkiri.secondhand.global.auth.infrastructure;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cokkiri.secondhand.global.auth.dto.response.JwtTokenResponse;
import com.cokkiri.secondhand.global.auth.entity.JwtAccessToken;
import com.cokkiri.secondhand.global.auth.entity.JwtRefreshToken;
import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.exception.list.IllegalJwtTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenGenerator  {

	private final String secret;
	private final Long accessTokenExpirationTime;
	private final Long refreshTokenExpirationTime;
	private final String accessSubject;
	private final String refreshSubject;
	private Key secretKey;

	public JwtTokenGenerator(
		MemoryJwtRepository memoryJwtRepository,
		@Value("${jwt.secret}") String secret,
		@Value("${jwt.access.expiration-time-in-milli-seconds}") Long accessTokenExpirationTime,
		@Value("${jwt.refresh.expiration-time-in-milli-seconds}") Long refreshTokenExpirationTime,
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

		throw new IllegalJwtTokenException(errorMessage);
	}

	private JwtAccessToken createAccessToken(UserInfoForJwt user) {
		Date expirationDate = getAccessExpirationDate();
		return JwtAccessToken.from(
			createToken(accessSubject, user.generateClaims(), expirationDate),
			convertToDateTime(expirationDate)
		);
	}

	private JwtRefreshToken createRefreshToken() {
		Date expirationDate = getRefreshExpirationDate();
		return JwtRefreshToken.from(
			createToken(refreshSubject, Map.of(), expirationDate),
			convertToDateTime(expirationDate)
		);
	}

	private String createToken(String subject, Map<String, String> claims, Date expirationDate) {
		return Jwts.builder()
			.setSubject(subject)
			.setClaims(claims)
			.setIssuedAt(new Date())
			.setExpiration(expirationDate)
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	private Date getAccessExpirationDate() {
		return new Date(System.currentTimeMillis() + accessTokenExpirationTime);
	}

	private Date getRefreshExpirationDate() {
		return new Date(System.currentTimeMillis() + refreshTokenExpirationTime);
	}

	private LocalDateTime convertToDateTime(Date date) {
		return date.toInstant() // Date -> Instant
			.atZone(ZoneId.systemDefault()) // Instant -> ZonedDateTime
			.toLocalDateTime();
	}
}
