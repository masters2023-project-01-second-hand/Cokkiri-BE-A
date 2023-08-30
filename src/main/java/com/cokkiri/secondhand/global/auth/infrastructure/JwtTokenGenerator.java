package com.cokkiri.secondhand.global.auth.infrastructure;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
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
public class JwtTokenGenerator implements InitializingBean  {

	private final String SECRET;
	private final Long ACCESS_EXPIRATION_TIME;
	private final Long REFRESH_EXPIRATION_TIME;
	private final String ACCESS_SUBJECT;
	private final String REFRESH_SUBJECT;
	private Key secretKey;

	public JwtTokenGenerator(
		MemoryJwtRepository memoryJwtRepository,
		@Value("${jwt.secret}") String SECRET,
		@Value("${jwt.access.expiration-time-in-milli-seconds}") Long ACCESS_EXPIRATION_TIME,
		@Value("${jwt.refresh.expiration-time-in-milli-seconds}") Long REFRESH_EXPIRATION_TIME,
		@Value("${jwt.access.subject}") String ACCESS_SUBJECT,
		@Value("${jwt.access.subject}") String REFRESH_SUBJECT) {

		this.SECRET = SECRET;
		this.ACCESS_EXPIRATION_TIME = ACCESS_EXPIRATION_TIME;
		this.REFRESH_EXPIRATION_TIME = REFRESH_EXPIRATION_TIME;
		this.ACCESS_SUBJECT = ACCESS_SUBJECT;
		this.REFRESH_SUBJECT = REFRESH_SUBJECT;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
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

	public UserInfoForJwt getUserForJwtBy(String token) {

		String errorMessage;

		try {
			Claims claims = Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody();

			return new UserInfoForJwt(
				(String) claims.get("id"),
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
		return new JwtAccessToken(
			createToken(ACCESS_SUBJECT, user.generateClaims(), expirationDate),
			convertToDateTime(expirationDate)
		);
	}

	private JwtRefreshToken createRefreshToken() {
		Date expirationDate = getRefreshExpirationDate();
		return new JwtRefreshToken(
			createToken(REFRESH_SUBJECT, Map.of(), expirationDate),
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
		return new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME);
	}

	private Date getRefreshExpirationDate() {
		return new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME);
	}

	private LocalDateTime convertToDateTime(Date date) {
		return date.toInstant() // Date -> Instant
			.atZone(ZoneId.systemDefault()) // Instant -> ZonedDateTime
			.toLocalDateTime();
	}
}
