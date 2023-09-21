package com.cokkiri.secondhand.item.dto.response;

import java.util.Objects;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.item.entity.Item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ItemResponseForAnyOne extends ItemResponse {

	private boolean isSeller;

	public static ItemResponseForAnyOne from(Item item, UserInfoForJwt userInfoForJwt) {
		return new ItemResponseForAnyOne(item, userInfoForJwt);
	}

	private ItemResponseForAnyOne(Item item, UserInfoForJwt userInfoForJwt) {
		super(item);
		this.isSeller = isSeller(item, userInfoForJwt);
	}

	private static boolean isSeller(Item item, UserInfoForJwt userInfoForJwt) {
		return Objects.equals(item.findSellerId(), userInfoForJwt.getUserId());
	}
}
