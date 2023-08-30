package com.cokkiri.secondhand.global.auth.entity;

import java.util.UUID;

import com.cokkiri.secondhand.user.entity.GitHubUser;
import com.cokkiri.secondhand.user.entity.Role;
import com.cokkiri.secondhand.user.entity.UserType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoFromOauthServer {

	private final String oauthId;
	private final String nickname;
	private final String imageUrl;

	public GitHubUser toGitHubUser() {
		return GitHubUser.builder()
			.oauthId(oauthId)
			.nickname(appendUuid(nickname))
			.profileImageUrl(imageUrl)
			.role(Role.USER)
			.userType(UserType.GITHUB)
			.build();
	}

	private String appendUuid(String nickname) {
		return String.format("%s&%s", nickname, UUID.randomUUID().toString());
	}
}
