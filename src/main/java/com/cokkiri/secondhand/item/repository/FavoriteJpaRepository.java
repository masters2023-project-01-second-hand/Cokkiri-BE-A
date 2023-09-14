package com.cokkiri.secondhand.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.item.entity.Favorite;

public interface FavoriteJpaRepository extends JpaRepository<Favorite, Long> {

	boolean existsByUserIdAndItemId(Long userId, Long itemId);

	void deleteByUserIdAndItemId(Long userId, Long itemId);
}
