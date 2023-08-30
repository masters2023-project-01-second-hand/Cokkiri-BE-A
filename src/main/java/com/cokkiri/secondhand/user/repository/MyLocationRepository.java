package com.cokkiri.secondhand.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.user.entity.MyLocation;

public interface MyLocationRepository extends JpaRepository<MyLocation, Long> {

}
