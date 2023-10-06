package com.cokkiri.secondhand.item.dto.response;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.item.entity.Item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ItemResponseForSpecificUser extends ItemResponse {

	public static ItemResponseForSpecificUser from(Item item, UserInfoForJwt userInfoForJwt) {
		return new ItemResponseForSpecificUser(item, userInfoForJwt);
	}

	private ItemResponseForSpecificUser(Item item, UserInfoForJwt userInfoForJwt) {
		super(item, userInfoForJwt);
	}
}
