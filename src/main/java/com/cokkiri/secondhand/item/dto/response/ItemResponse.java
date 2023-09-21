package com.cokkiri.secondhand.item.dto.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.item.entity.Item;
import com.cokkiri.secondhand.item.entity.ItemImage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ItemResponse {

	// 아이템 이미지 리스트의 첫 번째 이미지를 섬네일 이미지로 선정한다.
	private static final int THUMBNAIL_IMAGE_INDEX = 0;

	private Long id;
	private String title;
	private String locationName;
	private Date createAt;
	private String status;
	private Long price;
	private CountDataResponse countData;
	private String thumbnailUrl;
	private boolean isSeller;

	public static ItemResponse from(Item item, UserInfoForJwt userInfoForJwt) {
		return new ItemResponse(
			item.getId(),
			item.getTitle(),
			item.getLocation().getFullName(),
			item.getCreateAt(),
			item.getStatus().getStatusName(),
			item.getPrice(),
			null, //CountDataResponse.from(item.getItemMetadata()),	// TODO: countData 추가하기
			extractThumbnailUrl(item),			// TODO: image 추가하기
			isSeller(item, userInfoForJwt));			// TODO: isSeller 추가하기
	}

	private static String extractThumbnailUrl(Item item) {
		List<ItemImage> itemImage =new ArrayList<>();
		if(item.getItemImages().isEmpty()) {
			return null;
		}
		return item.getItemImages().get(THUMBNAIL_IMAGE_INDEX).getUrl();
	}

	private static boolean isSeller(Item item, UserInfoForJwt userInfoForJwt) {
		return Objects.equals(item.findSellerId(), userInfoForJwt.getUserId());
	}
}
