package com.cokkiri.secondhand.global.image.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ImageFileDirectory {

	private String userProfile;
	private String item;

	protected static String USER_PROFILE;
	protected static String ITEM;

	@Value("${image-storage.user.directory}")
	public static void setUserProfile(String userProfile) {
		ImageFileDirectory.USER_PROFILE = userProfile;
	}

	@Value("${image-storage.item.directory}")
	public static void setItem(String item) {
		ImageFileDirectory.ITEM = item;
	}
}
