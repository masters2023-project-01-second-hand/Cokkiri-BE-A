package com.cokkiri.secondhand.global.auth.infrastructure;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.cokkiri.secondhand.global.auth.entity.JwtRefreshToken;
import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.exception.list.NotExistRefreshTokenException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MemoryJwtRepository { // TODO: 추후 Redis로 변경하기

	private static final Map<UserInfoForJwt, JwtRefreshToken> refreshTokenStorage = new ConcurrentHashMap<>();

	public void save(UserInfoForJwt userInfoForJwt, JwtRefreshToken jwtRefreshToken) {
		refreshTokenStorage.put(userInfoForJwt, jwtRefreshToken);
	}

	public JwtRefreshToken get(UserInfoForJwt userInfoForJwt) {
		return refreshTokenStorage.get(userInfoForJwt);
	}

	public UserInfoForJwt getUserInfoForJwt(String refreshToken) {

		for (UserInfoForJwt userInfoForJwt : refreshTokenStorage.keySet()) {
			JwtRefreshToken jwtRefreshToken = refreshTokenStorage.get(userInfoForJwt);
			if (jwtRefreshToken.equalsRefreshToken(refreshToken)) {
				return userInfoForJwt;
			}
		}
		throw new NotExistRefreshTokenException();
	}

	public void removeRefreshToken(UserInfoForJwt userInfoForJwt) {
		refreshTokenStorage.remove(userInfoForJwt);
	}

	@Scheduled(initialDelay = 1000 * 60 * 60 * 24, fixedDelay = 1000 * 60 * 60)
	public void removeAllExpiredRefreshTokens() {

		log.info("removeAllExpiredRefreshTokens");

		for (UserInfoForJwt userInfoForJwt : refreshTokenStorage.keySet()) {
			if (refreshTokenStorage.get(userInfoForJwt).isExpired()) {
				refreshTokenStorage.remove(userInfoForJwt);
			}
		}
	}
}
