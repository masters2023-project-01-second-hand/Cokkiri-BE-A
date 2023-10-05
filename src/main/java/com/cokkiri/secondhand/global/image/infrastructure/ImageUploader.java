package com.cokkiri.secondhand.global.image.infrastructure;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

	String uploadMultiImageFile(MultipartFile multipartFile, ImageType imageType);

	List<String> uploadMultiImageFiles(List<MultipartFile> multipartFiles, ImageType imageType);
}
