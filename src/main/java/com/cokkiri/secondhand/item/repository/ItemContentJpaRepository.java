package com.cokkiri.secondhand.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.item.entity.ItemContent;

public interface ItemContentJpaRepository extends JpaRepository<ItemContent, Long> {

}
