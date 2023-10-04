package com.cokkiri.secondhand.item.dto.response;

import com.cokkiri.secondhand.item.entity.Item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ItemCreateResponse {

	private Long itemId;

	public static ItemCreateResponse from(Item item) {
		return new ItemCreateResponse(item.getId());
	}
}
