package com.cokkiri.secondhand.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.item.entity.ItemStatus;

public interface ItemStatusJpaRepository extends JpaRepository<ItemStatus, Long> {
}
