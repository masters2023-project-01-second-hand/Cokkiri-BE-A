package com.cokkiri.secondhand.item.dto.response;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.item.entity.Item;
import com.cokkiri.secondhand.item.entity.ItemStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ItemDetailResponse {

	private Boolean isSeller;
	private List<ItemImageResponse> images;
	private String seller;
	private List<ItemStatusResponse> status; // 3개 리스트 가져오기
	private String title;
	private String categoryName;
	private Date createdAt;
	private String content;
	private CountDataResponse countData;
	private Boolean isFavorite;
	private Long price;

	public static ItemDetailResponse from(Item item, UserInfoForJwt userInfoForJwt, List<ItemStatus> statuses, Boolean isFavorite) {
		return new ItemDetailResponse(
			isSeller(item, userInfoForJwt),
			item.getItemImages().stream()
				.map(ItemImageResponse::from)
				.collect(Collectors.toList()),
			item.getSeller().getNickname(),
			statuses.stream()
				.map(st ->ItemStatusResponse.from(st, item))
				.collect(Collectors.toList()),
			item.getTitle(),
			item.getCategory().getName(),
			item.getCreateAt(),
			item.getItemContent().getContent(),
			CountDataResponse.from(item.getItemMetadata()),
			isFavorite,
			item.getPrice()
		);
	}

	private static boolean isSeller(Item item, UserInfoForJwt userInfoForJwt) {
		return Objects.equals(item.findSellerId(), userInfoForJwt.getUserId());
	}
}
