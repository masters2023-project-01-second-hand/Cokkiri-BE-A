package com.cokkiri.secondhand.item.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.exception.list.NotFoundItemException;
import com.cokkiri.secondhand.global.exception.list.NotFoundLocationException;
import com.cokkiri.secondhand.global.exception.list.NotFoundUserException;
import com.cokkiri.secondhand.item.dto.response.ItemDetailResponse;
import com.cokkiri.secondhand.item.dto.response.ItemFavoriteResponse;
import com.cokkiri.secondhand.item.dto.response.ItemForUserResponse;
import com.cokkiri.secondhand.item.dto.response.ItemListForUserResponse;
import com.cokkiri.secondhand.item.dto.response.ItemListResponse;
import com.cokkiri.secondhand.item.dto.response.ItemResponse;
import com.cokkiri.secondhand.item.entity.Favorite;
import com.cokkiri.secondhand.item.entity.Item;
import com.cokkiri.secondhand.item.entity.Location;
import com.cokkiri.secondhand.item.entity.Status;
import com.cokkiri.secondhand.item.repository.FavoriteJpaRepository;
import com.cokkiri.secondhand.item.repository.ItemDslRepository;
import com.cokkiri.secondhand.item.repository.ItemJpaRepository;
import com.cokkiri.secondhand.item.repository.LocationJpaRepository;
import com.cokkiri.secondhand.user.entity.MyLocationList;
import com.cokkiri.secondhand.user.entity.UserEntity;
import com.cokkiri.secondhand.user.repository.MyLocationJpaRepository;
import com.cokkiri.secondhand.user.repository.UserEntityJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {

	private final UserEntityJpaRepository userEntityJpaRepository;
	private final ItemJpaRepository itemJpaRepository;
	private final ItemDslRepository itemDslRepository;
	private final LocationJpaRepository locationJpaRepository;
	private final MyLocationJpaRepository myLocationJpaRepository;
	private final FavoriteJpaRepository favoriteJpaRepository;

	private final ItemMetadataService itemMetadataService;

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

	@Transactional(readOnly = true)
	public ItemListForUserResponse getItemsForUser(String nickname, Boolean isSold, Long cursorId, Pageable pageable) {

		UserEntity user = userEntityJpaRepository.findByNickname(nickname)
			.orElseThrow(() -> new NotFoundUserException(nickname));

		List<ItemForUserResponse> items;

		Status status = (isSold == null)? null : Status.determineStatusIsSold(isSold);

		if (status == null) {
			items = itemDslRepository.findAllBySellerId(pageable, user.getId(), cursorId).stream()
				.map(ItemForUserResponse::from)
				.collect(Collectors.toList());
		} else {
			items = itemDslRepository.findAllBySellerIdAndStatusId(pageable, user.getId(), status.getId(), cursorId).stream()
				.map(ItemForUserResponse::from)
				.collect(Collectors.toList());
		}

		return new ItemListForUserResponse(items, calculateNextPageForUser(items, pageable));
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

	private Long calculateNextPageForUser(List<ItemForUserResponse> items, Pageable pageable) {
		if (items.isEmpty()) return null;

		if (items.size() < pageable.getPageSize()) return null;

		return items.get(items.size()-1).getId();
	}

	@Transactional
	public ItemDetailResponse getItemDetail(UserInfoForJwt userInfo, Long itemId) {

		Item item = itemJpaRepository.findById(itemId).orElseThrow(
			() -> new NotFoundItemException(itemId)
		);

		itemMetadataService.increaseHitCount(userInfo, item);

		return ItemDetailResponse.from(item);
	}

	@Transactional
	public ItemFavoriteResponse switchFavorite(UserInfoForJwt userInfo, Long itemId) {

		Item item = itemJpaRepository.findById(itemId).orElseThrow(
			() -> new NotFoundItemException(itemId)
		);

		if (existFavorite(userInfo, itemId)) {
			favoriteJpaRepository.deleteByUserIdAndItemId(userInfo.getUserId(), itemId);
			itemMetadataService.decreaseFavoriteCount(item);

			return ItemFavoriteResponse.from(false);
		}

		UserEntity user = userEntityJpaRepository.findById(userInfo.getUserId())
			.orElseThrow(() -> new NotFoundUserException(userInfo.getUserId()));

		Favorite favorite = Favorite.builder().user(user).item(item).build();

		favoriteJpaRepository.save(favorite);
		itemMetadataService.increaseFavoriteCount(item);

		return ItemFavoriteResponse.from(true);
	}

	private boolean existFavorite(UserInfoForJwt userInfo, Long itemId) {
		return favoriteJpaRepository.existsByUserIdAndItemId(userInfo.getUserId(), itemId);
	}
}
