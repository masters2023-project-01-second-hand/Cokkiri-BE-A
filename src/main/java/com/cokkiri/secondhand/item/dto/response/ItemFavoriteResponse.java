package com.cokkiri.secondhand.item.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ItemFavoriteResponse {

	private boolean isFavorite;

	public static ItemFavoriteResponse from(boolean isFavorite) {
		return new ItemFavoriteResponse(isFavorite);
	}
}
