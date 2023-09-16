package com.cokkiri.secondhand.item.repository;

import java.util.List;

import com.cokkiri.secondhand.item.dto.response.CategoryResponse;

public interface CategoryDslRepository {

	List<CategoryResponse> findAll();
}
