package com.cokkiri.secondhand.item.dto.response;

import com.cokkiri.secondhand.item.entity.ItemStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ItemStatusResponse {

	private Long id;
	private String name;
	private boolean isSelected;

	public static ItemStatusResponse from(ItemStatus status) {
		return new ItemStatusResponse(
			status.getId(), status.getStatusName(), false // TODO: 상태 리스트 만들기
		);
	}
}
