package com.cokkiri.secondhand.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.user.entity.GeneralUser;

public interface GeneralUserJpaRepository extends JpaRepository<GeneralUser, Long> {

	Optional<GeneralUser> findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByNickname(String nickname);
}
