package com.cokkiri.secondhand.item.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.exception.list.NotFoundLocationException;
import com.cokkiri.secondhand.item.dto.response.ItemListResponse;
import com.cokkiri.secondhand.item.dto.response.ItemResponse;
import com.cokkiri.secondhand.item.entity.Location;
import com.cokkiri.secondhand.item.repository.ItemDslRepository;
import com.cokkiri.secondhand.item.repository.LocationJpaRepository;
import com.cokkiri.secondhand.user.entity.MyLocationList;
import com.cokkiri.secondhand.user.repository.MyLocationJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {

	private final ItemDslRepository itemDslRepository;
	private final LocationJpaRepository locationJpaRepository;
	private final MyLocationJpaRepository myLocationJpaRepository;

	@Transactional(readOnly = true)
	public ItemListResponse getItems(Long cursorId, Long categoryId, Pageable pageable, UserInfoForJwt userInfoForJwt) {

		Location location = findLocation(userInfoForJwt);
		List<ItemResponse> items;

		if (categoryId == null) {
			items = itemDslRepository.findAllByLocationId(pageable, location.getId(), cursorId).stream()
				.map(item -> ItemResponse.from(item, userInfoForJwt))
				.collect(Collectors.toList());
		} else {
			items = itemDslRepository.findAllByCategoryIdAndLocationId(pageable, categoryId, location.getId(), cursorId).stream()
				.map(item -> ItemResponse.from(item, userInfoForJwt))
				.collect(Collectors.toList());
		}

		return new ItemListResponse(location.getDepth3(), items, calculateNextPage(items, pageable));
	}

	private Location findLocation(UserInfoForJwt userInfoForJwt) {

		if(userInfoForJwt.isGuest()) {
			return locationJpaRepository.findById(Location.getDefaultId())
				.orElseThrow(() -> new NotFoundLocationException(Location.getDefaultId()));
		}

		MyLocationList myLocations = new MyLocationList(myLocationJpaRepository.findAllBySelectedIsTrueAndUserId(userInfoForJwt.getUserId()));

		return myLocations.findSelectedLocation();
	}

	private Long calculateNextPage(List<ItemResponse> items, Pageable pageable) {
		if (items.isEmpty()) return null;

		if (items.size() < pageable.getPageSize()) return null;

		return items.get(items.size()-1).getId();
	}
}
