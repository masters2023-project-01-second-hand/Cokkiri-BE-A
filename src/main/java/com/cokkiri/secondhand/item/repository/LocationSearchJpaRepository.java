package com.cokkiri.secondhand.item.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cokkiri.secondhand.item.entity.Location;

public interface LocationSearchJpaRepository extends JpaRepository<Location, Long> {

	@Query(value = "SELECT id, depth_1, depth_2, depth_3 "
		+ "FROM location "
		+ "WHERE MATCH (depth_1, depth_2, depth_3) "
		+ "AGAINST (?1 IN NATURAL LANGUAGE MODE)", nativeQuery = true)
	Page<Location> search(String keyword, Pageable pageable);
}
