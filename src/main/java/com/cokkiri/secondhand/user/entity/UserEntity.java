package com.cokkiri.secondhand.user.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nickname", length = 100, unique = true, nullable = false)
	private String nickname;

	@Column(name = "profile_image_url", length = 2000)
	private String profileImageUrl;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Enumerated(EnumType.STRING)
	private UserType userType;

	@OneToMany
	private List<MyLocation> myLocations = new ArrayList<MyLocation>();

	public UserEntity(String nickname, String profileImageUrl, Role role, UserType userType) {
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
		this.role = role;
		this.userType = userType;
	}

	public void updateForGitHubUser(String nickname, String profileImageUrl) {
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}
}
