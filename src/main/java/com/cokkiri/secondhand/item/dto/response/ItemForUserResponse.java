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
public class ItemForUserResponse {

	private Long id;
	private String title;
	private String locationName;
	private Date createdAt;
	private String statusName;
	private Long price;
	private CountDataResponse countData;
	private String thumbnailUrl;

	public static ItemForUserResponse from(Item item) {
		return new ItemForUserResponse(
			item.getId(),
			item.getTitle(),
			item.getLocation().getFullName(),
			item.getCreateAt(),
			item.getStatus().getStatusName(),
			item.getPrice(),
			null, //CountDataResponse.from(item.getItemMetadata()),
			null
		);
	}
}
