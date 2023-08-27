package com.cokkiri.secondhand.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.cokkiri.secondhand.global.auth.jwt.JwtTokenGenerator;
import com.cokkiri.secondhand.global.filter.JwtAuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenGenerator jwtTokenGenerator;
	private final ObjectMapper objectMapper;
	private final JwtAuthorizationFilter jwtAuthorizationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.formLogin().disable()
			.httpBasic().disable()
			.csrf().disable()
			.headers().frameOptions().disable()
			.and()

			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()

			.authorizeRequests()
			.antMatchers("/api/auth/signup", "/api/auth/login").permitAll()
			.anyRequest().authenticated();

		http.addFilterAfter(jwtAuthorizationFilter, LogoutFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	/*
	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(jwtTokenGenerator, objectMapper);
	}*/
}
