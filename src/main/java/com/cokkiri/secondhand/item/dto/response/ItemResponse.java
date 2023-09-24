package com.cokkiri.secondhand.item.dto.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cokkiri.secondhand.item.entity.Item;
import com.cokkiri.secondhand.item.entity.ItemImage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ItemResponse {

	// 아이템 이미지 리스트의 첫 번째 이미지를 섬네일 이미지로 선정한다.
	private static final int THUMBNAIL_IMAGE_INDEX = 0;

	private Long id;
	private String title;
	private String locationName;
	private Date createdAt;
	private String statusName;
	private Long price;
	private CountDataResponse countData;
	private String thumbnailUrl;

	public ItemResponse(Item item) {
		this.id = item.getId();
		this.title = item.getTitle();
		this.locationName = item.getLocation().getFullName();
		this.createdAt = item.getCreateAt();
		this.statusName = item.getStatus().getStatusName();
		this.price = item.getPrice();
		this.countData = CountDataResponse.from(item.getItemMetadata());
		this.thumbnailUrl = extractThumbnailUrl(item);
	}

	private static String extractThumbnailUrl(Item item) {
		List<ItemImage> itemImage =new ArrayList<>();
		if(item.getItemImages().isEmpty()) {
			return null;
		}
		return item.getItemImages().get(THUMBNAIL_IMAGE_INDEX).getUrl();
	}
}
