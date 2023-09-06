package com.cokkiri.secondhand.item.dto.response;

import com.cokkiri.secondhand.item.entity.Location;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LocationNameResponse {

	private Long id;
	private String name;

	public static LocationNameResponse from(Location location) {
		return new LocationNameResponse(
			location.getId(),
			location.getFullName()
		);
	}
}
