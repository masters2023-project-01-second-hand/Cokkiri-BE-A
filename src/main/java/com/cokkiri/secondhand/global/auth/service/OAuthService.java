package com.cokkiri.secondhand.global.auth.service;

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

import com.cokkiri.secondhand.global.auth.entity.OAuthType;
import com.cokkiri.secondhand.global.auth.entity.UserInfoFromOauthServer;
import com.cokkiri.secondhand.global.exception.list.NotFoundLocationException;
import com.cokkiri.secondhand.item.entity.Location;
import com.cokkiri.secondhand.item.repository.LocationJpaRepository;
import com.cokkiri.secondhand.user.entity.GitHubUser;
import com.cokkiri.secondhand.user.entity.MyLocation;
import com.cokkiri.secondhand.user.repository.GitHubUserJpaRepository;
import com.cokkiri.secondhand.user.repository.MyLocationJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final GitHubUserJpaRepository gitHUbUserJpaRepository;
	private final LocationJpaRepository locationJpaRepository;
	private final MyLocationJpaRepository myLocationJpaRepository;

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

		Location location = locationJpaRepository.findByName(Location.getDefaultName())
			.orElseThrow(() -> new NotFoundLocationException(Location.getDefaultName()));

		MyLocation myLocation = MyLocation.builder()
			.location(location)
			.user(gitHubUser)
			.isSelected(Boolean.TRUE)
			.build();
		myLocationJpaRepository.save(myLocation);

		return new DefaultOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority(gitHubUser.getRole().name())),
			attributes,
			userNameAttributeName);
	}

	private GitHubUser saveOrUpdate(UserInfoFromOauthServer userInfo) {
		GitHubUser gitHubUser = gitHUbUserJpaRepository.findByOauthId(userInfo.getOauthId())
			.map(user -> user.update(
				userInfo.getOauthId(),
				userInfo.getNickname(),
				userInfo.getImageUrl()))
			.orElse(userInfo.toGitHubUser());
		return gitHUbUserJpaRepository.save(gitHubUser);
	}
}
