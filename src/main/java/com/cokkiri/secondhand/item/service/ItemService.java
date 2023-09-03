package com.cokkiri.secondhand.item.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokkiri.secondhand.item.dto.response.ItemListResponse;
import com.cokkiri.secondhand.item.dto.response.ItemResponse;
import com.cokkiri.secondhand.item.repository.ItemJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {

	private final ItemJpaRepository itemJpaRepository;

	@Transactional(readOnly = true)
	public ItemListResponse getItemsByCategory(long categoryId, Pageable pageable) {

		Page<ItemResponse> items
			= itemJpaRepository.findAllByCategory_Id(pageable, categoryId)
			.map(ItemResponse::from);

		return new ItemListResponse(items.getContent(), 1L, false);
	}
}
