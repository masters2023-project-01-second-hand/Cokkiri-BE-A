package com.cokkiri.secondhand.item.dto.response;

import com.cokkiri.secondhand.item.entity.Item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ItemResponseForSpecificUser extends ItemResponse {

	public static ItemResponseForSpecificUser from(Item item) {
		return new ItemResponseForSpecificUser(item);
	}

	private ItemResponseForSpecificUser(Item item) {
		super(item);
	}
}
