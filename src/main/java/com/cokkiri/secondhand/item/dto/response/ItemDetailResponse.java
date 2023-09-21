package com.cokkiri.secondhand.item.dto.response;

import java.util.Date;
import java.util.Objects;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.item.entity.Item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ItemDetailResponse {

	private Boolean isSeller;
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

	public static ItemDetailResponse from(Item item, UserInfoForJwt userInfoForJwt) {
		return new ItemDetailResponse(
			isSeller(item, userInfoForJwt), // 판매자인지 아닌지 비교
			item.getSeller().getNickname(),
			null, // status list를 가져와야함
			item.getTitle(),
			item.getCategory().getName(),
			item.getCreateAt(),
			item.getItemContent().getContent(),
			item.getPrice()
		);
	}

	private static boolean isSeller(Item item, UserInfoForJwt userInfoForJwt) {
		return Objects.equals(item.findSellerId(), userInfoForJwt.getUserId());
	}
}
