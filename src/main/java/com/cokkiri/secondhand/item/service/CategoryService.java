package com.cokkiri.secondhand.item.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cokkiri.secondhand.item.dto.response.CategoryListResponse;
import com.cokkiri.secondhand.item.dto.response.CategoryResponse;
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
}
