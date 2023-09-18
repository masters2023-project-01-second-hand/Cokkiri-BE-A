package com.cokkiri.secondhand.item.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cokkiri.secondhand.item.dto.response.CategoryListResponse;
import com.cokkiri.secondhand.item.dto.response.CategoryResponse;
import com.cokkiri.secondhand.item.repository.CategoryDslRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {

	private final CategoryDslRepository categoryDslRepository;

	public CategoryListResponse getAllCategories() {
		return
			new CategoryListResponse(
				categoryDslRepository.findAll()
			);
	}

	public CategoryListResponse getRecommendedCategories() {
		List<CategoryResponse> categories = categoryDslRepository.findAll();

		Collections.shuffle(categories);
		List<CategoryResponse> recommendedCategories = categories.subList(0, 3);

		return
			new CategoryListResponse(
				recommendedCategories
			);
	}
}
