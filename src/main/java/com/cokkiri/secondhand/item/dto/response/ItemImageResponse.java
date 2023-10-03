package com.cokkiri.secondhand.item.dto.response;

import com.cokkiri.secondhand.item.entity.ItemImage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ItemImageResponse {

	private Long id;
	private String url;

	public static ItemImageResponse from(ItemImage itemImage) {
		return new ItemImageResponse(
			itemImage.getId(), itemImage.getUrl()
		);
	}
}
