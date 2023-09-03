package com.cokkiri.secondhand.global.auth.dto.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GeneralSignUpRequest {

	@NotBlank(message = "아이디는 필수 값입니다.")
	@Length(max = 100, message = "아이디는 최대 100글자 입니다.")
	private String username;

	@NotBlank(message = "닉네임은 필수 값입니다.")
	@Length(max = 100, message = "닉네임은 최대 100글자 입니다.")
	private String nickname;

	@NotBlank(message = "비밀번호는 필수 값입니다.")
	@Length(max = 100, message = "비밀번호는 최대 100글자 입니다.")
	private String password;
}
