package com.cokkiri.secondhand.global.auth.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cokkiri.secondhand.global.auth.dto.request.GeneralSignUpRequest;
import com.cokkiri.secondhand.global.auth.service.GeneralAuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class GeneralAuthController {

	private final GeneralAuthService generalAuthService;

	@PostMapping("/api/auth/signup")
	public ResponseEntity<Void> signUp(
		@RequestBody @Valid GeneralSignUpRequest signUpRequest) {
		System.out.println("??");
		generalAuthService.signUp(signUpRequest);

		return ResponseEntity.ok().build();
	}
}
