package com.cokkiri.secondhand.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "GITHUB_USER")
@PrimaryKeyJoinColumn(name = "user_id")
public class GitHubUser extends UserEntity {

	@Column(name = "oauth_id", length = 100, unique = true, nullable = false)
	private String oauthId;

	public GitHubUser(String nickname, String profileImageUrl, Role role, String oauthId) {
		super(nickname, profileImageUrl, role, UserType.GITHUB);
		this.oauthId = oauthId;
	}

	public GitHubUser update(String oauthId, String name, String imageUrl) {
		this.oauthId = oauthId;
		super.updateForGitHubUser(name, imageUrl);
		return this;
	}
}
