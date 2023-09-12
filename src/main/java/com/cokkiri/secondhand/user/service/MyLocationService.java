package com.cokkiri.secondhand.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.exception.list.DuplicateMyLocationException;
import com.cokkiri.secondhand.global.exception.list.LimitExceededMyLocationException;
import com.cokkiri.secondhand.global.exception.list.MyLocationDeletionNotAllowedException;
import com.cokkiri.secondhand.global.exception.list.NotFoundLocationException;
import com.cokkiri.secondhand.global.exception.list.NotFoundMyLocationException;
import com.cokkiri.secondhand.global.exception.list.NotFoundUserException;
import com.cokkiri.secondhand.item.entity.Location;
import com.cokkiri.secondhand.item.repository.LocationJpaRepository;
import com.cokkiri.secondhand.user.dto.request.AddMyLocationRequest;
import com.cokkiri.secondhand.user.dto.response.MyLocationListResponse;
import com.cokkiri.secondhand.user.dto.response.MyLocationResponse;
import com.cokkiri.secondhand.user.entity.MyLocation;
import com.cokkiri.secondhand.user.entity.UserEntity;
import com.cokkiri.secondhand.user.repository.MyLocationJpaRepository;
import com.cokkiri.secondhand.user.repository.UserEntityJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyLocationService {

	private final UserEntityJpaRepository userEntityJpaRepository;
	private final LocationJpaRepository locationJpaRepository;
	private final MyLocationJpaRepository myLocationJpaRepository;

	public MyLocationListResponse getMyLocationByUserId(UserInfoForJwt userInfoForJwt) {

		List<MyLocation> myLocations
			= myLocationJpaRepository.findAllByUserId(userInfoForJwt.getUserId());

		List<MyLocationResponse> mySelectedLocations = myLocations.stream()
			.filter(myLocation -> !myLocation.isSelected())
			.map(MyLocationResponse::from)
			.collect(Collectors.toUnmodifiableList());

		return new MyLocationListResponse(mySelectedLocations);
	}

	@Transactional
	public MyLocationResponse addMyLocation(AddMyLocationRequest addMyLocationRequest, UserInfoForJwt userInfoForJwt) {

		UserEntity user = userEntityJpaRepository.findById(userInfoForJwt.getUserId())
			.orElseThrow(() -> new NotFoundUserException(userInfoForJwt.getUserId()));

		Location location = locationJpaRepository.findById(addMyLocationRequest.getLocationId())
			.orElseThrow(() -> new NotFoundLocationException(addMyLocationRequest.getLocationId()));

		if (isMyLocationListLimit(user)) {
			throw new LimitExceededMyLocationException();
		}

		if (existMyLocation(user, location)) {
			throw new DuplicateMyLocationException(location.getFullName());
		}

		MyLocation myLocation = MyLocation.builder()
			.user(user)
			.location(location)
			.selected(false)
			.build();

		return MyLocationResponse.from(myLocationJpaRepository.save(myLocation));
	}

	@Transactional
	public void chooseMyLocation(Long myLocationId, UserInfoForJwt userInfoForJwt) {

		Long userId = userInfoForJwt.getUserId();

		myLocationJpaRepository.updateAllIsSelectedToFalse(userId);

		MyLocation myLocation = myLocationJpaRepository.findByIdAndUserId(myLocationId, userId)
			.orElseThrow(() -> new NotFoundMyLocationException(myLocationId));
		myLocation.chooseMyLocation();

		myLocationJpaRepository.save(myLocation);
	}

	@Transactional
	public void removeMyLocation(Long myLocationId, UserInfoForJwt userInfoForJwt) {

		Long userId = userInfoForJwt.getUserId();

		UserEntity user = userEntityJpaRepository.findById(userId)
			.orElseThrow(() -> new NotFoundUserException(userId));

		MyLocation myLocation = myLocationJpaRepository.findByIdAndUserId(myLocationId, userId)
			.orElseThrow(() -> new NotFoundMyLocationException(myLocationId));

		if (myLocation.isSelected()) {
			throw new MyLocationDeletionNotAllowedException();
		}

		myLocationJpaRepository.delete(myLocation);
	}

	private boolean existMyLocation(UserEntity user, Location location) {
		return myLocationJpaRepository.existsByUserIdAndLocationId(user.getId(), location.getId());
	}

	private boolean isMyLocationListLimit(UserEntity user) {
		return myLocationJpaRepository.countByUserId(user.getId()) >= 3;
	}
}
