package com.cokkiri.secondhand.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.item.entity.Location;

public interface LocationJpaRepository extends JpaRepository<Location, Long> {

	Optional<Location> findById(Long id);
}
