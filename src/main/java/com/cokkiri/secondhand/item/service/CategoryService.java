package com.cokkiri.secondhand.item.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cokkiri.secondhand.item.dto.response.CategoryListResponse;
import com.cokkiri.secondhand.item.dto.response.CategoryResponse;
import com.cokkiri.secondhand.item.entity.Category;
import com.cokkiri.secondhand.item.repository.CategoryJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {

	private final CategoryJpaRepository categoryJpaRepository;

	public CategoryListResponse getAllCategories() {
		return
			new CategoryListResponse(
				categoryJpaRepository.findAll()
					.stream()
					.map(CategoryResponse::from)
					.collect(Collectors.toList())
			);
	}

	public CategoryListResponse getRecommendedCategories() {
		List<Category> categories = categoryJpaRepository.findAll();

		Collections.shuffle(categories);
		List<Category> recommendedCategories = categories.subList(0, 3);

		return
			new CategoryListResponse(
				recommendedCategories
					.stream()
					.map(CategoryResponse::from)
					.collect(Collectors.toList())
			);
	}
}
