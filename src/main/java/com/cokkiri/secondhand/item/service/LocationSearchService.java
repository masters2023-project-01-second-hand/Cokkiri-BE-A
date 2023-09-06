package com.cokkiri.secondhand.item.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokkiri.secondhand.item.dto.response.LocationNameListResponse;
import com.cokkiri.secondhand.item.dto.response.LocationNameResponse;
import com.cokkiri.secondhand.item.repository.LocationSearchJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LocationSearchService {

	private static final String DEFAULT_KEYWORD = "서울 역삼";
	private static final String SINGLE_LETTER_REGEX = "[ㄱ-ㅎㅏ-ㅣ]";

	private final Pattern pattern = Pattern.compile(SINGLE_LETTER_REGEX);

	private final LocationSearchJpaRepository locationSearchJpaRepository;

	@Transactional
	public LocationNameListResponse searchLocations(String keyword, Pageable pageable) {
		keyword = removeSingleLetter(keyword);

		switch (countNumberOfCharactersBy(keyword)) {
			case 0:
				return searchLocationsFor2OrMoreCharacter(DEFAULT_KEYWORD, pageable);
			case 1:
				return searchLocationsForOneCharacter(keyword, pageable);
			default:
				return searchLocationsFor2OrMoreCharacter(keyword, pageable);
		}
	}

	private LocationNameListResponse searchLocationsForOneCharacter(String keyword, Pageable pageable) {
		Slice<LocationNameResponse> locationNames = locationSearchJpaRepository.searchForOneCharacter(keyword, pageable)
			.map(LocationNameResponse::from);

		return new LocationNameListResponse(
			locationNames.getContent(),
			calculateNextPage(locationNames)
		);
	}

	private LocationNameListResponse searchLocationsFor2OrMoreCharacter(String keyword, Pageable pageable) {
		Slice<LocationNameResponse> locationNames = locationSearchJpaRepository.searchFor2OrMoreCharacter(keyword, pageable)
			.map(LocationNameResponse::from);

		return new LocationNameListResponse(
			locationNames.getContent(),
			calculateNextPage(locationNames)
		);
	}

	private String removeSingleLetter(String keyword) {
		Matcher matcher = pattern.matcher(keyword);
		return matcher.replaceAll("");
	}

	private int countNumberOfCharactersBy(String keyword) {
		return keyword.replace(" ", "").length();
	}

	private Long calculateNextPage(Slice<LocationNameResponse> locationNames) {
		if(locationNames.hasNext()) {
			return locationNames.getNumber() + 2L;
		}
		return null;
	}
}
