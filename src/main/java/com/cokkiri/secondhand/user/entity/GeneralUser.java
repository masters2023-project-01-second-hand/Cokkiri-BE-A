package com.cokkiri.secondhand.user.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "GENERAL_USER")
@PrimaryKeyJoinColumn(name = "user_id")
public class GeneralUser extends UserEntity {

	@Column(name = "username", length = 100, unique = true, nullable = false)
	private String username;

	@Column(name = "password", length = 1000, unique = true, nullable = false)
	private String password;

	public GeneralUser(String nickname, String profileImageUrl, Role role, String username,
		String password) {
		super(nickname, profileImageUrl, role, UserType.GENERAL);
		this.username = username;
		this.password = password;
	}

	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}

	public boolean validatePassword(String password, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(password, this.password);
	}

	public boolean validatePassword(String encodedInputPassword) {
		return Objects.equals(this.password, encodedInputPassword);
	}

}
