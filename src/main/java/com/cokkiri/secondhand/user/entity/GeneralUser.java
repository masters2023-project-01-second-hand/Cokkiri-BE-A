package com.cokkiri.secondhand.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user")
public class GeneralUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username", length = 100, unique = true, nullable = false)
	private String username;

	@Column(name = "password", length = 1000, unique = true, nullable = false)
	private String password;

	@Column(name = "nickname", length = 100, unique = true, nullable = false)
	private String nickname;

	@Column(name = "profile_image_url", length = 2000)
	private String profileImageUrl;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	public GeneralUser(String username, String password, String nickname, String profileImageUrl, Role role) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
		this.role = role;
	}

	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}
}
