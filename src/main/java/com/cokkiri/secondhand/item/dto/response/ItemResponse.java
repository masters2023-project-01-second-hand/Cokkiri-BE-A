package com.cokkiri.secondhand.item.dto.response;

import java.util.Date;

import com.cokkiri.secondhand.global.utill.PriceFormatter;
import com.cokkiri.secondhand.item.entity.Item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ItemResponse {

	private Long id;
	private String title;
	private String locationName;
	private Date createAt;
	private String status;
	private String price;
	private CountDataResponse countData;
	private String thumbnailUrl;

	public static ItemResponse from(Item item) {
		return new ItemResponse(
			item.getId(),
			item.getTitle(),
			item.getLocation().getName(),
			item.getCreateAt(),
			item.getStatus().getName().getName(),
			PriceFormatter.addCommasTo(item.getPrice()),
			null,	// TODO: countData 추가하기
			null);			// TODO: image 추가하기
	}
}
