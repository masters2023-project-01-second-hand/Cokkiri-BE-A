package com.cokkiri.secondhand.item.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cokkiri.secondhand.item.dto.response.CategoryListResponse;
import com.cokkiri.secondhand.item.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping("/api/categories")
	public ResponseEntity<CategoryListResponse> getAllCategories() {

		return ResponseEntity.ok(categoryService.getAllCategories());
	}
}
