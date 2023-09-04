package com.cokkiri.secondhand.item.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Location {

	private static final Long DEFAULT_LOCATION_ID = 1970L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(length = 100, name = "depth_1")
	private String depth1;

	@Column(length = 100, name = "depth_2")
	private String depth2;

	@Column(length = 100, name = "depth_3")
	private String depth3;

	public static Long getDefaultId() {
		return DEFAULT_LOCATION_ID;
	}

	public String getFullName() {
		return String.format("%s %s %s", depth1, depth2, depth3);
	}
}
