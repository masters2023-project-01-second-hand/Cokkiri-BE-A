package com.cokkiri.secondhand.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cokkiri.secondhand.user.entity.GitHubUser;

public interface GitHubUserJpaRepository extends JpaRepository<GitHubUser, Long> {

	Optional<GitHubUser> findByOauthId(String oauthId);
}
