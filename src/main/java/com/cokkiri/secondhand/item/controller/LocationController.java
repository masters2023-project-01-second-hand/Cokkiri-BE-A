package com.cokkiri.secondhand.item.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cokkiri.secondhand.item.dto.response.LocationNameListResponse;
import com.cokkiri.secondhand.item.service.LocationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LocationController {

	private final LocationService locationService;


	@GetMapping("/api/locations")
	public ResponseEntity<LocationNameListResponse> searchLocations(
		@RequestParam(required = false) String query,
		@RequestParam(required = false, defaultValue = "0") int cursor) {

		return ResponseEntity.ok(locationService.searchLocations(query, cursor));
	}
}
