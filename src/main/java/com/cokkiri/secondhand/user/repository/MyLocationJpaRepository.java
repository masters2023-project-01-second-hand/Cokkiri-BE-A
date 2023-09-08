package com.cokkiri.secondhand.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cokkiri.secondhand.user.entity.MyLocation;

public interface MyLocationJpaRepository extends JpaRepository<MyLocation, Long> {

	Optional<MyLocation> findByIdAndUserId(Long id, Long userId);

	List<MyLocation> findAllByUserId(Long userId);

	List<MyLocation> findAllBySelectedIsTrueAndUserId(Long userId);

	boolean existsByUserIdAndLocationId(Long userId, Long locationId);

	Long countByUserId(Long userId);

	@Modifying
	@Query(value = "UPDATE my_location "
		+ "SET is_selected = false "
		+ "WHERE user_id = ?1", nativeQuery = true)
	void updateAllIsSelectedToFalse(Long useId);
}
