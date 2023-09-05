package com.cokkiri.secondhand.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.exception.list.DuplicateMyLocationException;
import com.cokkiri.secondhand.global.exception.list.LimitExceededMyLocationException;
import com.cokkiri.secondhand.global.exception.list.NotFoundLocationException;
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

		List<MyLocationResponse> locations
			= myLocationJpaRepository.findAllByUserId(
				userInfoForJwt.getIdAsLong()).stream()
			.filter(myLocation -> !myLocation.isSelected())
			.map(MyLocationResponse::from)
			.collect(Collectors.toUnmodifiableList());

		return new MyLocationListResponse(locations);
	}

	@Transactional
	public MyLocationResponse addMyLocation(AddMyLocationRequest addMyLocationRequest, UserInfoForJwt userInfoForJwt) {

		UserEntity user = userEntityJpaRepository.findById(userInfoForJwt.getIdAsLong())
			.orElseThrow(() -> new NotFoundUserException(userInfoForJwt.getIdAsLong()));

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
			.isSelected(false)
			.build();

		return MyLocationResponse.from(myLocationJpaRepository.save(myLocation));
	}

	private boolean existMyLocation(UserEntity user, Location location) {
		return myLocationJpaRepository.existsByUserIdAndLocationId(user.getId(), location.getId());
	}

	private boolean isMyLocationListLimit(UserEntity user) {
		return myLocationJpaRepository.countByUserId(user.getId()) >= 3;
	}
}
