package com.cokkiri.secondhand.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cokkiri.secondhand.item.entity.ItemMetadata;

public interface ItemMetadataJpaRepository extends JpaRepository<ItemMetadata, Long> {

	@Query(value = "SELECT id, chat, favorite, hit FROM item_metadata WHERE id = :id FOR UPDATE", nativeQuery = true)
	Optional<ItemMetadata> findByIdWithPessimisticLock(@Param("id") Long id);
}
