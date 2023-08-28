package com.cokkiri.secondhand.global.auth.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cokkiri.secondhand.global.auth.dto.request.GeneralLogInRequest;
import com.cokkiri.secondhand.global.auth.dto.request.GeneralSignUpRequest;
import com.cokkiri.secondhand.global.auth.dto.response.JwtTokenResponse;
import com.cokkiri.secondhand.global.auth.service.GeneralAuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class GeneralAuthController {

	private final GeneralAuthService generalAuthService;

	@PostMapping("/api/auth/login")
	public ResponseEntity<JwtTokenResponse> login(
		@RequestBody @Valid GeneralLogInRequest signInRequest) {

		return ResponseEntity.ok(
			generalAuthService.logIn(signInRequest));
	}

	@PostMapping("/api/auth/signup")
	public ResponseEntity<Void> signUp(
		@RequestBody @Valid GeneralSignUpRequest signUpRequest) {

		generalAuthService.signUp(signUpRequest);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/lol")
	public ResponseEntity<String> sdfs() {
		return ResponseEntity.ok("sdfsdfsdf");
	}
}
