package com.cokkiri.secondhand.global.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.cokkiri.secondhand.global.auth.dto.properties.AwsS3Credentials;

@Configuration
public class AwsS3Config {

	private final AwsS3Credentials awsS3Credentials;

	@Value("${cloud.aws.region.static}")
	private String region;

	public AwsS3Config(AwsS3Credentials awsS3Credentials) {
		this.awsS3Credentials = awsS3Credentials;
	}

	@Bean
	@Primary
	public BasicAWSCredentials awsCredentialsProvider(){
		return new BasicAWSCredentials(
			awsS3Credentials.getAccessKey(),
			awsS3Credentials.getSecretKey());
	}

	@Bean
	public AmazonS3 amazonS3() {
		ClientConfiguration clientConfiguration = new ClientConfiguration();
		clientConfiguration.setConnectionTimeout(10_000);
		clientConfiguration.setRequestTimeout(10_000);
		clientConfiguration.setMaxErrorRetry(3);

		return AmazonS3ClientBuilder.standard()
			.withRegion(region)
			.withCredentials(new AWSStaticCredentialsProvider(awsCredentialsProvider()))
			.withClientConfiguration(clientConfiguration)
			.build();
	}
}
