package com.cokkiri.secondhand.item.repository;

import static com.cokkiri.secondhand.item.entity.QItem.*;

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

		BooleanBuilder builder = generateBooleanBuilderWithCursorCondition(cursorId)
			.and(item.location.id.eq(locationId));

		return findAllBy(pageable, builder);
	}

	public List<Item> findAllByCategoryIdAndLocationId(Pageable pageable, Long categoryId, Long locationId, Long cursorId) {

		BooleanBuilder builder = generateBooleanBuilderWithCursorCondition(cursorId)
			.and(item.category.id.eq(categoryId))
			.and(item.location.id.eq(locationId));

		return findAllBy(pageable, builder);
	}

	public List<Item> findAllBySellerId(Pageable pageable, Long sellerId, Long cursorId) {

		BooleanBuilder builder = generateBooleanBuilderWithCursorCondition(cursorId)
			.and(item.seller.id.eq(sellerId));

		return findAllBy(pageable, builder);
	}

	public List<Item> findAllBySellerIdAndStatusId(Pageable pageable, Long sellerId, Long statusId, Long cursorId) {

		BooleanBuilder builder = generateBooleanBuilderWithCursorCondition(cursorId)
			.and(item.seller.id.eq(sellerId))
			.and(item.status.id.eq(statusId));

		return findAllBy(pageable, builder);
	}

	private BooleanBuilder generateBooleanBuilderWithCursorCondition(Long cursorId) {

		BooleanBuilder builder = new BooleanBuilder();
		if (cursorId != null) {
			builder.and(item.id.lt(cursorId));
		}

		return builder;
	}

	private List<Item> findAllBy(Pageable pageable, BooleanBuilder builder) {

		return queryFactory
			.selectFrom(item)
			.where(builder)
			.orderBy(item.id.desc())
			.limit(pageable.getPageSize())
			.fetch();
	}
}
