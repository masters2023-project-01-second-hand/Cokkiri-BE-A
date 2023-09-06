package com.cokkiri.secondhand.item.dto.response;

import com.cokkiri.secondhand.item.entity.Category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CategoryResponse {

	private Long id;
	private String name;
	private String iconName;

	public static CategoryResponse from(Category category) {
		return new CategoryResponse(
			category.getId(),
			category.getName(),
			category.getName()
		);
	}
}
