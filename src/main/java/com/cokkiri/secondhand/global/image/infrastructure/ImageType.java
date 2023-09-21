package com.cokkiri.secondhand.global.image.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageType {

	USER_PROFILE("images/user"),
	ITEM("images/item");

	private final String imageFileDirectory;
}
