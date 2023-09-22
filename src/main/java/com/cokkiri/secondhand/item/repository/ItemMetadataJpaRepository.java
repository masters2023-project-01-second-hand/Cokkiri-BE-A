package com.cokkiri.secondhand.item.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cokkiri.secondhand.item.entity.ItemMetadata;

public interface ItemMetadataJpaRepository extends JpaRepository<ItemMetadata, Long> {

	@Lock(LockModeType.OPTIMISTIC)
	@Query("SELECT im FROM ItemMetadata im WHERE im.id = :id")
	Optional<ItemMetadata> findByIdWithOptimisticLock(@Param("id") Long id);
}
