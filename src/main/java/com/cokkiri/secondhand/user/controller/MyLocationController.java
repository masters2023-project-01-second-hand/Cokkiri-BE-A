package com.cokkiri.secondhand.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.user.dto.response.MyLocationListResponse;
import com.cokkiri.secondhand.user.service.MyLocationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MyLocationController {

	private final MyLocationService myLocationService;

	@GetMapping("/api/users/locations")
	public ResponseEntity<MyLocationListResponse> getMyLocations(HttpServletRequest request) {

		UserInfoForJwt userInfoForJwt = (UserInfoForJwt)request.getAttribute("userInfoForJwt");

		return ResponseEntity.ok(
			myLocationService.getMyLocationByUserId(userInfoForJwt)
		);
	}
}
