package com.cokkiri.secondhand.item.repository;

import static com.cokkiri.secondhand.item.entity.QCategory.*;
import static com.cokkiri.secondhand.item.entity.QItem.*;
import static com.cokkiri.secondhand.item.entity.QLocation.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.cokkiri.secondhand.item.entity.Item;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ItemDslRepositoryImpl implements ItemDslRepository {

	private final JPAQueryFactory queryFactory;

	public ItemDslRepositoryImpl(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	public List<Item> findAllByLocationId(Pageable pageable, Long locationId, Long cursorId) {

		BooleanBuilder dynamicLtId = new BooleanBuilder();

		if (cursorId != null) {
			dynamicLtId.and(item.id.lt(cursorId));
		}

		return queryFactory
			.selectFrom(item)
			.leftJoin(location).on(location.id.eq(item.location.id))
			.where(dynamicLtId.and(location.id.eq(locationId)))
			.orderBy(item.id.desc())
			.limit(pageable.getPageSize())
			.fetch();
	}

	public List<Item> findAllByCategoryIdAndLocationId(Pageable pageable, Long categoryId, Long locationId, Long cursorId) {

		BooleanBuilder dynamicLtId = new BooleanBuilder();

		if (cursorId != null) {
			dynamicLtId.and(item.id.lt(cursorId));
		}

		return queryFactory
			.selectFrom(item)
			.leftJoin(category).on(category.id.eq(item.category.id))
			.leftJoin(location).on(location.id.eq(item.location.id))
			.where(
				dynamicLtId
					.and(category.id.eq(categoryId))
					.and(location.id.eq(locationId)))
			.orderBy(item.id.desc())
			.limit(pageable.getPageSize())
			.fetch();
	}
}
