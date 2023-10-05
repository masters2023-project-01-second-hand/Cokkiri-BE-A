package com.cokkiri.secondhand.item.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.exception.list.IllegalStatusException;
import com.cokkiri.secondhand.global.exception.list.NotFoundCategoryException;
import com.cokkiri.secondhand.global.exception.list.NotFoundLocationException;
import com.cokkiri.secondhand.global.exception.list.NotFoundMyLocationException;
import com.cokkiri.secondhand.global.exception.list.NotFoundUserException;
import com.cokkiri.secondhand.global.image.infrastructure.ImageType;
import com.cokkiri.secondhand.global.image.infrastructure.ImageUploader;
import com.cokkiri.secondhand.item.dto.request.ItemCreateRequest;
import com.cokkiri.secondhand.item.dto.response.ItemCreateResponse;
import com.cokkiri.secondhand.item.entity.Category;
import com.cokkiri.secondhand.item.entity.Item;
import com.cokkiri.secondhand.item.entity.ItemContent;
import com.cokkiri.secondhand.item.entity.ItemImage;
import com.cokkiri.secondhand.item.entity.ItemStatus;
import com.cokkiri.secondhand.item.entity.Location;
import com.cokkiri.secondhand.item.entity.Status;
import com.cokkiri.secondhand.item.repository.CategoryJpaRepository;
import com.cokkiri.secondhand.item.repository.ItemContentJpaRepository;
import com.cokkiri.secondhand.item.repository.ItemImageJpaRepository;
import com.cokkiri.secondhand.item.repository.ItemJpaRepository;
import com.cokkiri.secondhand.item.repository.ItemStatusJpaRepository;
import com.cokkiri.secondhand.item.repository.LocationJpaRepository;
import com.cokkiri.secondhand.user.entity.MyLocation;
import com.cokkiri.secondhand.user.entity.UserEntity;
import com.cokkiri.secondhand.user.repository.MyLocationJpaRepository;
import com.cokkiri.secondhand.user.repository.UserEntityJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemCreateService {

	private final UserEntityJpaRepository userEntityJpaRepository;
	private final ItemJpaRepository itemJpaRepository;
	private final LocationJpaRepository locationJpaRepository;
	private final CategoryJpaRepository categoryJpaRepository;
	private final MyLocationJpaRepository myLocationJpaRepository;
	private final ItemStatusJpaRepository itemStatusJpaRepository;
	private final ItemContentJpaRepository itemContentJpaRepository;
	private final ItemImageJpaRepository itemImageJpaRepository;

	private final ImageUploader imageUploader;

	@Transactional
	public ItemCreateResponse createItem(UserInfoForJwt userInfo,
		List<MultipartFile> imageFiles, ItemCreateRequest itemCreateRequest) {

		Item savedItem = saveItem(userInfo, itemCreateRequest);
		saveItemContent(savedItem, itemCreateRequest);
		saveItemImages(savedItem, imageFiles);

		return ItemCreateResponse.from(savedItem);
	}

	private Item saveItem(UserInfoForJwt userInfo, ItemCreateRequest itemCreateRequest) {

		Long userId = userInfo.getUserId();
		Status status = Status.SALE;

		UserEntity seller = userEntityJpaRepository.findById(userId)
			.orElseThrow(() -> new NotFoundUserException(userId));

		MyLocation myLocation = myLocationJpaRepository.findByIdAndUserId(itemCreateRequest.getMyLocationId(), userId)
			.orElseThrow(() -> new NotFoundMyLocationException(itemCreateRequest.getMyLocationId()));

		Location location = locationJpaRepository.findById(myLocation.findLocationId())
			.orElseThrow(() -> new NotFoundLocationException(myLocation.findLocationId()));

		ItemStatus itemStatus = itemStatusJpaRepository.findByName(status)
			.orElseThrow(() -> new IllegalStatusException(status.getName()));

		Category category = categoryJpaRepository.findById(itemCreateRequest.getCategoryId())
			.orElseThrow(() -> new NotFoundCategoryException(itemCreateRequest.getCategoryId()));

		Item item = Item.builder()
			.title(itemCreateRequest.getTitle())
			.price(itemCreateRequest.getPrice())
			.seller(seller)
			.location(location)
			.status(itemStatus)
			.category(category)
			.build();

		return itemJpaRepository.save(item);
	}

	private void saveItemContent(Item item, ItemCreateRequest itemCreateRequest) {

		ItemContent itemContent = ItemContent.builder()
			.item(item)
			.content(itemCreateRequest.getContent())
			.build();

		itemContentJpaRepository.save(itemContent);
	}

	private void saveItemImages(Item item, List<MultipartFile> imageFiles) {

		List<String> imageUrls = imageUploader.uploadMultiImageFiles(imageFiles, ImageType.ITEM);

		List<ItemImage> itemImages = imageUrls.stream()
			.map(img
				-> ItemImage.builder()
					.item(item)
					.url(img)
					.build())
			.collect(Collectors.toList());

		itemImageJpaRepository.saveAll(itemImages);
	}


}
