package com.cokkiri.secondhand.global.image.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageType {

	USER_PROFILE(ImageFileDirectory.USER_PROFILE),
	ITEM(ImageFileDirectory.ITEM);

	private final String imageFileDirectory;
}
