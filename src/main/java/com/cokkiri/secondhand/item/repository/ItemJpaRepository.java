package com.cokkiri.secondhand.item.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.item.entity.Item;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {

	Slice<Item> findAllByLocationId(Pageable pageable, Long locationId);

	Slice<Item> findAllByCategoryIdAndLocationId(Pageable pageable, Long categoryId, Long locationId);
}
