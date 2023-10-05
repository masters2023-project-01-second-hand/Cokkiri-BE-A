package com.cokkiri.secondhand.global.auth.dto.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "cloud.aws.credentials")
@Setter
@Getter
public class AwsS3Credentials {

	private String accessKey;
	private String secretKey;
}

