package com.cokkiri.secondhand.user.dto.response;

import com.cokkiri.secondhand.user.entity.GeneralUser;

import lombok.Getter;

@Getter
public class GeneralUserInfoResponse {

	private String nickname;
	private String profileImageUrl;

	public GeneralUserInfoResponse(String nickname, String profileImageUrl) {
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}

	public static GeneralUserInfoResponse from(GeneralUser generalUser) {
		return new GeneralUserInfoResponse(
			generalUser.getNickname(),
			generalUser.getProfileImageUrl()
		);
	}
}
