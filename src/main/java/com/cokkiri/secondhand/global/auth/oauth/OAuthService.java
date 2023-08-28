package com.cokkiri.secondhand.global.auth.oauth;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cokkiri.secondhand.user.entity.GitHubUser;
import com.cokkiri.secondhand.user.repository.GitHubUserRepository;

@Service
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final GitHubUserRepository gitHUbUserRepository;

	public OAuthService(GitHubUserRepository gitHUbUserRepository) {
		this.gitHUbUserRepository = gitHUbUserRepository;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration()
			.getRegistrationId();

		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
			.getUserInfoEndpoint().getUserNameAttributeName();
		Map<String, Object> attributes = oAuth2User.getAttributes();

		UserInfoFromOauthServer userInfo = OAuthType.extract(registrationId, attributes);

		GitHubUser gitHubUser = saveOrUpdate(userInfo);

		return new DefaultOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority(gitHubUser.getRole().name())),
			attributes,
			userNameAttributeName);
	}

	private GitHubUser saveOrUpdate(UserInfoFromOauthServer userInfo) {
		GitHubUser user = gitHUbUserRepository.findByOauthId(userInfo.getOauthId())
			.map(u -> u.update(
				userInfo.getOauthId(),
				userInfo.getName(),
				userInfo.getImageUrl()))
			.orElse(userInfo.toGitHubUser());
		return gitHUbUserRepository.save(user);
	}
}
