package com.cokkiri.secondhand.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.user.entity.MyLocation;

public interface MyLocationJpaRepository extends JpaRepository<MyLocation, Long> {

	List<MyLocation> findAllByUserId(Long userId);
}
