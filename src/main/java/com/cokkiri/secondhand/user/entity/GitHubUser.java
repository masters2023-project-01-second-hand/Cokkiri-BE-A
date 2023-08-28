package com.cokkiri.secondhand.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "github_user")
public class GitHubUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "oauth_id", length = 100, unique = true, nullable = false)
	private String oauthId;

	@Column(name = "nickname", length = 500, unique = true, nullable = false)
	private String nickName;

	@Column(name = "profile_image_url", length = 2000)
	private String profileImageUrl;

	@Enumerated(EnumType.STRING)
	private Role role;

	public GitHubUser(String oauthId, String nickName, String profileImageUrl, Role role) {
		this.oauthId = oauthId;
		this.nickName = nickName;
		this.profileImageUrl = profileImageUrl;
		this.role = role;
	}

	public GitHubUser update(String oauthId, String name, String imageUrl) {
		this.oauthId = oauthId;
		this.nickName = name;
		this.profileImageUrl = imageUrl;
		return this;
	}
}
