package com.cokkiri.secondhand.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.user.dto.response.MyLocationListResponse;
import com.cokkiri.secondhand.user.dto.response.MyLocationResponse;
import com.cokkiri.secondhand.user.repository.MyLocationJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyLocationService {

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
}
