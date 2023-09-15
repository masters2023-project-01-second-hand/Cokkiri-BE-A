package com.cokkiri.secondhand.item.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.cokkiri.secondhand.item.entity.Item;

public interface ItemDslRepository {

	List<Item> findAllByLocationId(Pageable pageable, Long locationId, Long cursorId);

	List<Item> findAllByCategoryIdAndLocationId(Pageable pageable, Long categoryId, Long locationId, Long cursorId);

	List<Item> findAllBySellerId(Pageable pageable, Long sellerId, Long cursorId);

	List<Item> findAllBySellerIdAndStatusId(Pageable pageable, Long sellerId, Long statusId, Long cursorId);
}
