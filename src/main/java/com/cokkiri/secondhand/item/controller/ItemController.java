package com.cokkiri.secondhand.item.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cokkiri.secondhand.item.dto.response.ItemListResponse;
import com.cokkiri.secondhand.item.service.ItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ItemController {

	private final ItemService itemService;

	@GetMapping("/api/items")
	public ResponseEntity<ItemListResponse> showItems(
		@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
		@RequestParam(required = false) int categoryId) {

		return ResponseEntity.ok(
			itemService.getItemsByCategory(categoryId, pageNo));
	}
}
