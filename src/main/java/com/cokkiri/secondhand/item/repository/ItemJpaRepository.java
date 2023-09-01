package com.cokkiri.secondhand.item.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.item.entity.Item;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {

	Page<Item> findAllByCategory_Id(Pageable pageable, Long categoryId);
}
