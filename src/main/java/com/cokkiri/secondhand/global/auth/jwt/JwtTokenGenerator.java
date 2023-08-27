package com.cokkiri.secondhand.global.auth.jwt;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cokkiri.secondhand.global.auth.domain.UserInfoForJwt;
import com.cokkiri.secondhand.global.auth.dto.response.JwtTokenResponse;
import com.cokkiri.secondhand.global.exception.IllegalJwtTokenException;

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
		return new JwtTokenResponse(
			createAccessToken(user),
			createRefreshToken()
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

	private String createAccessToken(UserInfoForJwt user) {
		return createToken(ACCESS_SUBJECT, user.generateClaims(), getAccessExpirationDate());
	}

	private String createRefreshToken() {
		return createToken(REFRESH_SUBJECT, Map.of(), getRefreshExpirationDate());
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
}
