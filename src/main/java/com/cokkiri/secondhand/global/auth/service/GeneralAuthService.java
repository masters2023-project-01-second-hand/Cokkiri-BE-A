package com.cokkiri.secondhand.global.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokkiri.secondhand.global.auth.dto.request.GeneralLogInRequest;
import com.cokkiri.secondhand.global.auth.dto.request.GeneralSignUpRequest;
import com.cokkiri.secondhand.global.auth.dto.response.JwtTokenResponse;
import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.exception.list.LoginFailureException;
import com.cokkiri.secondhand.global.exception.list.NicknameDuplicationException;
import com.cokkiri.secondhand.global.exception.list.NotFoundLocationException;
import com.cokkiri.secondhand.global.exception.list.UsernameDuplicationException;
import com.cokkiri.secondhand.item.entity.Location;
import com.cokkiri.secondhand.item.repository.LocationJpaRepository;
import com.cokkiri.secondhand.user.entity.GeneralUser;
import com.cokkiri.secondhand.user.entity.MyLocation;
import com.cokkiri.secondhand.user.entity.Role;
import com.cokkiri.secondhand.user.repository.GeneralUserJpaRepository;
import com.cokkiri.secondhand.user.repository.MyLocationJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class GeneralAuthService {

	private final JwtTokenService jwtTokenService;
	private final GeneralUserJpaRepository generalUserJpaRepository;
	private final LocationJpaRepository locationJpaRepository;
	private final MyLocationJpaRepository myLocationJpaRepository;
	private final PasswordEncoder passwordEncoder;

	public JwtTokenResponse logIn(GeneralLogInRequest logInRequest) {
		GeneralUser user = generalUserJpaRepository.findByUsername(
			logInRequest.getUsername()
		).orElseThrow(LoginFailureException::new);

		if (!validatePassword(logInRequest, user)) {
			throw new LoginFailureException();
		}

		return jwtTokenService.issueTokens(UserInfoForJwt.from(user));
	}

	public void signUp(GeneralSignUpRequest signUpRequest) {

		String username = signUpRequest.getUsername();
		String nickname = signUpRequest.getNickname();

		if(isDuplicateUsername(username)) {
			throw new UsernameDuplicationException(username);
		}

		if(isDuplicateNickname(nickname)) {
			throw new NicknameDuplicationException(nickname);
		}

		GeneralUser user = GeneralUser.builder()
			.username(username)
			.password(signUpRequest.getPassword())
			.nickname(nickname)
			.role(Role.USER)
			.build();
		user.encodePassword(passwordEncoder);

		Location location = locationJpaRepository.findById(Location.getDefaultId())
			.orElseThrow(() -> new NotFoundLocationException(Location.getDefaultId()));

		MyLocation myLocation = MyLocation.builder()
			.location(location)
			.user(user)
			.selected(Boolean.TRUE)
			.build();

		generalUserJpaRepository.save(user);
		myLocationJpaRepository.save(myLocation);
	}

	public boolean isDuplicateUsername(String username) {
		return generalUserJpaRepository.existsByUsername(username);
	}

	public boolean isDuplicateNickname(String nickname) {
		return generalUserJpaRepository.existsByNickname(nickname);
	}

	private boolean validatePassword(GeneralLogInRequest logInRequest, GeneralUser user) {
		return passwordEncoder.matches(logInRequest.getPassword(), user.getPassword());
	}
}
