package com.cokkiri.secondhand.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.item.entity.Category;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

	List<Category> findAll();
}
