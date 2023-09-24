package com.cokkiri.secondhand.item.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.item.dto.response.ItemDetailResponse;
import com.cokkiri.secondhand.item.dto.response.ItemFavoriteResponse;
import com.cokkiri.secondhand.item.dto.response.ItemForSpecificUserListResponse;
import com.cokkiri.secondhand.item.dto.response.ItemForAnyOneListResponse;
import com.cokkiri.secondhand.item.service.ItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ItemController {

	private final ItemService itemService;

	@GetMapping("/api/items")
	public ResponseEntity<ItemForAnyOneListResponse> showItems(
		HttpServletRequest request,
		@RequestParam(name = "cursor", required = false) Long cursorId,
		@RequestParam(required = false) Long categoryId) {

		UserInfoForJwt userInfoForJwt = (UserInfoForJwt)request.getAttribute("userInfoForJwt");

		return ResponseEntity.ok(
			itemService.getItems(cursorId, categoryId, PageRequest.of(0, 10), userInfoForJwt));
	}

	@GetMapping("/api/users/{nickname}/items")
	public ResponseEntity<ItemForSpecificUserListResponse> showItemsForSpecificUser(
		@PathVariable String nickname,
		@RequestParam(required = false) Boolean isSold,
		@RequestParam(name = "cursor", required = false) Long cursorId) {

		return ResponseEntity.ok(
			itemService.getItemsForUser(nickname, isSold, cursorId, PageRequest.of(0, 10))
		);
	}

	@GetMapping("/api/items/{itemId}")
	public ResponseEntity<ItemDetailResponse> showItemDetail(
		HttpServletRequest request, @PathVariable Long itemId) {

		UserInfoForJwt userInfoForJwt = (UserInfoForJwt)request.getAttribute("userInfoForJwt");

		return ResponseEntity.ok(
			itemService.getItemDetail(userInfoForJwt, itemId)
		);
	}

	@PatchMapping("/api/items/{itemId}/favorite")
	public ResponseEntity<ItemFavoriteResponse> addFavorite(
		HttpServletRequest request, @PathVariable Long itemId) {

		UserInfoForJwt userInfoForJwt = (UserInfoForJwt)request.getAttribute("userInfoForJwt");

		return ResponseEntity.ok(itemService.switchFavorite(userInfoForJwt, itemId));
	}
}
