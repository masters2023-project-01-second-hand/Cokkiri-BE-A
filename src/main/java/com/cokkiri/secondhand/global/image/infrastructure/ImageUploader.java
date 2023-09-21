package com.cokkiri.secondhand.global.image.infrastructure;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

	String uploadMultiImageFile(MultipartFile multipartFile, ImageType imageType);
}
