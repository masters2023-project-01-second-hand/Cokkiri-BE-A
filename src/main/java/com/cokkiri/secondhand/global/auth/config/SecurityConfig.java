package com.cokkiri.secondhand.global.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.cokkiri.secondhand.global.auth.infrastructure.JwtTokenGenerator;
import com.cokkiri.secondhand.global.auth.handler.OAuthLoginFailureHandler;
import com.cokkiri.secondhand.global.auth.handler.OAuthLoginSuccessHandler;
import com.cokkiri.secondhand.global.auth.service.OAuthService;
import com.cokkiri.secondhand.global.auth.filter.JwtAuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenGenerator jwtTokenGenerator;
	private final ObjectMapper objectMapper;
	private final JwtAuthorizationFilter jwtAuthorizationFilter;

	private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
	private final OAuthLoginFailureHandler oAuthLoginFailureHandler;
	private final OAuthService oAuthService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.formLogin().disable()
			.httpBasic().disable()
			.csrf().disable()
			.headers().frameOptions().disable()
			.and()

			.cors()
			.and()

			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()

			.authorizeRequests()
			.antMatchers("/api/users",
				"/api/login",
				"/api/reissue-access-token",
				"/api/items").permitAll()
			.anyRequest().authenticated()
			.and()

			.oauth2Login()
			.successHandler(oAuthLoginSuccessHandler)
			.failureHandler(oAuthLoginFailureHandler)
			.userInfoEndpoint()
			.userService(oAuthService);

		http.addFilterAfter(jwtAuthorizationFilter, LogoutFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
