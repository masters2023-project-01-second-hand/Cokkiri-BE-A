package com.cokkiri.secondhand.item.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ItemListResponse {

	// private String userLocation; // TODO: 현재 로그인된 유저의 위치 정보 추가하기
	private List<ItemResponse> items;
	// private boolean lastPage; // TODO: 마지막 페이지인지 여부 추가하기
}
