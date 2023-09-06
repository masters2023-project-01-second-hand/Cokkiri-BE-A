package com.cokkiri.secondhand.item.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cokkiri.secondhand.item.dto.response.LocationNameListResponse;
import com.cokkiri.secondhand.item.service.LocationSearchService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LocationController {

	private final LocationSearchService locationSearchService;

	@GetMapping("/api/locations")
	public ResponseEntity<LocationNameListResponse> searchLocations(
		@PageableDefault(size = 15, page = 0) Pageable pageable,
		@RequestParam(required = false) String query) {

		return ResponseEntity.ok(locationSearchService.searchLocations(query, pageable));
	}
}
