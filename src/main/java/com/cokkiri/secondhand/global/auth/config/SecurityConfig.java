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

import com.cokkiri.secondhand.global.auth.filter.JwtAuthorizationFilter;
import com.cokkiri.secondhand.global.auth.handler.OAuthLoginFailureHandler;
import com.cokkiri.secondhand.global.auth.handler.OAuthLoginSuccessHandler;
import com.cokkiri.secondhand.global.auth.service.OAuthService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

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
			.antMatchers("/**").permitAll() // Allow all requests
			.and()
			.oauth2Login()
			.successHandler(oAuthLoginSuccessHandler)
			.failureHandler(oAuthLoginFailureHandler)
			.userInfoEndpoint()
			.userService(oAuthService);

		// http.addFilterAfter(jwtAuthorizationFilter, LogoutFilter.class); // Optionally, you can comment out this line if you don't want to use the JWT filter at all.

		return http.build();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
