package com.cokkiri.secondhand.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.user.entity.UserEntity;

public interface UserEntityJpaRepository extends JpaRepository<UserEntity, Long> {
}
