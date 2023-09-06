package com.cokkiri.secondhand.user.dto.response;

import com.cokkiri.secondhand.user.entity.MyLocation;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MyLocationResponse {

	private Long id;
	private String name;
	@JsonProperty("isSelected")
	private Boolean isSelected;

	public static MyLocationResponse from(MyLocation myLocation) {
		return new MyLocationResponse(
			myLocation.getId(),
			myLocation.getLocation().getFullName(),
			myLocation.isSelected()
		);
	}
}
