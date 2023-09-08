package com.cokkiri.secondhand.item.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.item.dto.response.ItemListResponse;
import com.cokkiri.secondhand.item.service.ItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ItemController {

	private final ItemService itemService;

	@GetMapping("/api/items")
	public ResponseEntity<ItemListResponse> showItems(
		HttpServletRequest request,
		@RequestParam(name = "cursor", required = false) Long cursorId,
		@RequestParam(required = false) Long categoryId) {

		UserInfoForJwt userInfoForJwt = (UserInfoForJwt)request.getAttribute("userInfoForJwt");

		return ResponseEntity.ok(
			itemService.getItems(cursorId, categoryId, PageRequest.of(0, 10), userInfoForJwt));
	}
}
