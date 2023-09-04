package com.cokkiri.secondhand.item.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokkiri.secondhand.item.dto.response.LocationNameListResponse;
import com.cokkiri.secondhand.item.dto.response.LocationNameResponse;
import com.cokkiri.secondhand.item.repository.LocationSearchJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LocationService {

	private final LocationSearchJpaRepository locationSearchJpaRepository;

	@Transactional
	public LocationNameListResponse searchLocations(String keyword, int cursor) {

		Pageable pageable = PageRequest.of(cursor, 10);

		List<LocationNameResponse> locationNames = locationSearchJpaRepository.search(keyword, pageable)
			.map(LocationNameResponse::from).getContent();

		return new LocationNameListResponse(
			locationNames,
			cursor + 1L
		);
	}
}
