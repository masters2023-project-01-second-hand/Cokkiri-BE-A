package com.cokkiri.secondhand.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.user.entity.UserEntity;

public interface UserEntityJpaRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findById(Long id);
}
