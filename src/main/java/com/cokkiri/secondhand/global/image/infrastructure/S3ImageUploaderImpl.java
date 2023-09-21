package com.cokkiri.secondhand.global.image.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cokkiri.secondhand.global.exception.list.FileUploadFailureException;
import com.cokkiri.secondhand.global.exception.list.IllegalFileStateException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3ImageUploaderImpl implements ImageUploader {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private final AmazonS3 amazonS3;

	public String uploadMultiImageFile(MultipartFile multipartFile, ImageType imageType) {

		if (!validateMultiFile(multipartFile)) {
			if (imageType == ImageType.USER_PROFILE) {
				return "https://imgur.com/a/2ndgaq9";
			}

			throw new IllegalFileStateException(multipartFile);
		}

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(multipartFile.getContentType());
		objectMetadata.setContentLength(multipartFile.getSize());

		String originalFileName = multipartFile.getOriginalFilename();
		assert originalFileName != null;
		String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
		String fileKey = imageType.getImageFileDirectory() + "/" + UUID.randomUUID() + "." + extension;

		try (InputStream inputStream = multipartFile.getInputStream()) {
			amazonS3.putObject(new PutObjectRequest(bucket, fileKey, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			throw new FileUploadFailureException();
		}
		return amazonS3.getUrl(bucket, fileKey).toString();
	}

	/**
	 * 유효한 파일이면 ture 반환
	 * @param multipartFile 검증할 파일
	 * @return 유효한 파일이면 ture 반환
	 */
	private boolean validateMultiFile(MultipartFile multipartFile) {
		return !(multipartFile == null
			|| multipartFile.isEmpty()
			|| multipartFile.getOriginalFilename() == null
			|| multipartFile.getOriginalFilename().isBlank()
			|| multipartFile.getSize() == 0);
	}
}
