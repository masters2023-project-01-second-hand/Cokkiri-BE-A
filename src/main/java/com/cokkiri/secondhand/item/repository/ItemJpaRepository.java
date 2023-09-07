package com.cokkiri.secondhand.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.item.entity.Item;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {
}
