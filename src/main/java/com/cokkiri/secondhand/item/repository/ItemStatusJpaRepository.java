package com.cokkiri.secondhand.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.item.entity.ItemStatus;
import com.cokkiri.secondhand.item.entity.Status;

public interface ItemStatusJpaRepository extends JpaRepository<ItemStatus, Long> {

	Optional<ItemStatus> findByName(Status name);
}
