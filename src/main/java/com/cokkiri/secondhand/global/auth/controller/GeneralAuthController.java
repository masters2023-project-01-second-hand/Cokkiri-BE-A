package com.cokkiri.secondhand.global.auth.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cokkiri.secondhand.global.auth.dto.request.GeneralLogInRequest;
import com.cokkiri.secondhand.global.auth.dto.request.GeneralSignUpRequest;
import com.cokkiri.secondhand.global.auth.dto.response.LoginResponse;
import com.cokkiri.secondhand.global.auth.infrastructure.JwtAuthHttpResponseManager;
import com.cokkiri.secondhand.global.auth.service.GeneralAuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class GeneralAuthController {

	private final JwtAuthHttpResponseManager jwtAuthHttpResponseManager;
	private final GeneralAuthService generalAuthService;

	@PostMapping("/api/login")
	public ResponseEntity<LoginResponse> login(
		@RequestBody @Valid GeneralLogInRequest signInRequest,
		HttpServletResponse response) {

		LoginResponse loginResponse = generalAuthService.logIn(signInRequest);

		jwtAuthHttpResponseManager.setAuthHttpResponse(response, loginResponse.getJwtTokenResponse());

		return ResponseEntity.ok(loginResponse);
	}

	@PostMapping("/api/users")
	public ResponseEntity<Void> signUp(@RequestPart(required = false) MultipartFile profileImageFile,
		@RequestPart("signupData") @Valid GeneralSignUpRequest signUpRequest) {

		generalAuthService.signUp(signUpRequest, profileImageFile);

		return ResponseEntity.ok().build();
	}
}
