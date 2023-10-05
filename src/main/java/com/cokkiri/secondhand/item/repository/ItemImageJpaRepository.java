package com.cokkiri.secondhand.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.item.entity.ItemImage;

public interface ItemImageJpaRepository extends JpaRepository<ItemImage, Long> {
}
