package com.cokkiri.secondhand.global.auth.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthType {

	GITHUB("github", (attributes) -> {
		return new UserInfoFromOauthServer(
			String.valueOf(attributes.get("id")),
			(String) attributes.get("login"),
			(String) attributes.get("avatar_url")
		);
	});

	private final String registrationId;
	private final Function<Map<String, Object>, UserInfoFromOauthServer> of;

	OAuthType(String registrationId, Function<Map<String, Object>, UserInfoFromOauthServer> of) {
		this.registrationId = registrationId;
		this.of = of;
	}

	public static UserInfoFromOauthServer extract(String registrationId, Map<String, Object> attributes) {
		return Arrays.stream(values())
			.filter(provider -> registrationId.equals(provider.registrationId))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new)
			.of.apply(attributes);
	}
}
