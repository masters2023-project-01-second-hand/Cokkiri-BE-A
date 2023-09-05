package com.cokkiri.secondhand.item.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cokkiri.secondhand.item.entity.Location;

public interface LocationSearchJpaRepository extends JpaRepository<Location, Long> {

	@Query(value = "SELECT id, depth_1, depth_2, depth_3 "
		+ "FROM location "
		+ "WHERE depth_1 LIKE %?1% "
		+ "OR depth_2 LIKE %?1% "
		+ "OR depth_3 LIKE %?1%", nativeQuery = true)
	Slice<Location> searchForOneCharacter(String keyword, Pageable pageable);

	@Query(value = "SELECT id, depth_1, depth_2, depth_3 "
		+ "FROM location "
		+ "WHERE MATCH (depth_1, depth_2, depth_3) "
		+ "AGAINST (?1 IN NATURAL LANGUAGE MODE)", nativeQuery = true)
	Slice<Location> searchFor2OrMoreCharacter(String keyword, Pageable pageable);
}
