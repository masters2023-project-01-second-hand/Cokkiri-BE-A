package com.cokkiri.secondhand.item.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.exception.list.NotFoundLocationException;
import com.cokkiri.secondhand.item.dto.response.ItemListResponse;
import com.cokkiri.secondhand.item.dto.response.ItemResponse;
import com.cokkiri.secondhand.item.entity.Location;
import com.cokkiri.secondhand.item.repository.ItemJpaRepository;
import com.cokkiri.secondhand.item.repository.LocationJpaRepository;
import com.cokkiri.secondhand.user.entity.MyLocationList;
import com.cokkiri.secondhand.user.repository.MyLocationJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {

	private final ItemJpaRepository itemJpaRepository;
	private final LocationJpaRepository locationJpaRepository;
	private final MyLocationJpaRepository myLocationJpaRepository;

	@Transactional(readOnly = true)
	public ItemListResponse getItems(Long categoryId, Pageable pageable, UserInfoForJwt userInfoForJwt) {

		Location location = findLocation(userInfoForJwt);
		Slice<ItemResponse> items;

		if (categoryId == null) {
			items = itemJpaRepository.findAllByLocationId(pageable, location.getId())
				.map(item -> ItemResponse.from(item, userInfoForJwt));
		} else {
			items = itemJpaRepository.findAllByCategoryIdAndLocationId(pageable, categoryId, location.getId())
				.map(item -> ItemResponse.from(item, userInfoForJwt));
		}

		return new ItemListResponse(location.getDepth3(), items.getContent(), calculateNextPage(items));
	}

	private Location findLocation(UserInfoForJwt userInfoForJwt) {

		if(userInfoForJwt.isGuest()) {
			return locationJpaRepository.findById(Location.getDefaultId())
				.orElseThrow(() -> new NotFoundLocationException(Location.getDefaultId()));
		}

		MyLocationList myLocations = new MyLocationList(myLocationJpaRepository.findAllBySelectedIsTrueAndUserId(userInfoForJwt.getUserId()));

		return myLocations.findSelectedLocation();
	}

	private Long calculateNextPage(Slice<ItemResponse> items) {
		if(items.hasNext()) {
			return items.getNumber() + 2L;
		}
		return null;
	}
}
