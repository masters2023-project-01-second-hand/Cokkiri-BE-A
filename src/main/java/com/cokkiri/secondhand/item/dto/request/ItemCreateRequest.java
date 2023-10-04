package com.cokkiri.secondhand.item.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ItemCreateRequest {

	@NotNull
	private String title;

	@NotNull
	private Long categoryId;

	private Long price;

	private String content;

	@NotNull
	private Long myLocationId;
}
