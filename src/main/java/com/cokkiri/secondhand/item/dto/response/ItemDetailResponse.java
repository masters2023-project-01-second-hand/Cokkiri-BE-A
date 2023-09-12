package com.cokkiri.secondhand.item.dto.response;

import java.util.Date;

import com.cokkiri.secondhand.item.entity.Item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ItemDetailResponse {

	private boolean isSeller;
	// images
	private String seller;
	private ItemStatusListResponse status;
	private String title;
	private String categoryName;
	private Date createdAt;
	private String content;
	// countData
	// isFavorite
	private Long price;

	public static ItemDetailResponse from(Item item) {
		return new ItemDetailResponse(
			false,
			item.getSeller().getNickname(),
			null,
			item.getTitle(),
			item.getCategory().getName(),
			item.getCreateAt(),
			item.getItemContent().getContent(),
			item.getPrice()
		);
	}
}
