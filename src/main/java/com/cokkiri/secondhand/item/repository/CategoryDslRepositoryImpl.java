package com.cokkiri.secondhand.item.repository;

import static com.cokkiri.secondhand.item.entity.QCategory.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.cokkiri.secondhand.item.dto.response.CategoryResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CategoryDslRepositoryImpl implements CategoryDslRepository {

	private final JPAQueryFactory queryFactory;

	public CategoryDslRepositoryImpl(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	public List<CategoryResponse> findAll() {

		return queryFactory
			.select(
				Projections.constructor(
					CategoryResponse.class,
					category.id,
					category.name,
					category.name))
			.from(category)
			.fetch();
	}
}
