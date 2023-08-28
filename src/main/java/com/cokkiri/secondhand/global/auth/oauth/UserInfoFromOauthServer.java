package com.cokkiri.secondhand.global.auth.oauth;

import com.cokkiri.secondhand.user.entity.GitHubUser;
import com.cokkiri.secondhand.user.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoFromOauthServer {

	private final String oauthId;
	private final String name;
	private final String imageUrl;

	public GitHubUser toGitHubUser() {
		return new GitHubUser(
			oauthId,
			name,
			imageUrl,
			Role.USER
		);
	}
}
