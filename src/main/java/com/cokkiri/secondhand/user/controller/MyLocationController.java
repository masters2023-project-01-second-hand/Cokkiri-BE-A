package com.cokkiri.secondhand.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.user.dto.request.AddMyLocationRequest;
import com.cokkiri.secondhand.user.dto.response.MyLocationListResponse;
import com.cokkiri.secondhand.user.dto.response.MyLocationResponse;
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

	@PostMapping("/api/users/locations")
	public ResponseEntity<MyLocationResponse> addMyLocation(HttpServletRequest request, @RequestBody AddMyLocationRequest addMyLocationRequest) {

		UserInfoForJwt userInfoForJwt = (UserInfoForJwt)request.getAttribute("userInfoForJwt");

		return ResponseEntity.ok(myLocationService.addMyLocation(addMyLocationRequest, userInfoForJwt));
	}

	@PatchMapping("/api/users/locations/{myLocationId}")
	public ResponseEntity<Void> chooseMyLocation(HttpServletRequest request, @PathVariable Long myLocationId) {

		UserInfoForJwt userInfoForJwt = (UserInfoForJwt)request.getAttribute("userInfoForJwt");

		myLocationService.chooseMyLocation(myLocationId, userInfoForJwt);

		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/api/users/locations/{myLocationId}")
	public ResponseEntity<Void> removeMyLocation(HttpServletRequest request, @PathVariable Long myLocationId) {

		UserInfoForJwt userInfoForJwt = (UserInfoForJwt)request.getAttribute("userInfoForJwt");

		myLocationService.removeMyLocation(myLocationId, userInfoForJwt);

		return ResponseEntity.noContent().build();
	}
}
