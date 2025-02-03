package com.webapp.websiteportal.service;

import org.springframework.web.multipart.MultipartFile;

import com.webapp.websiteportal.dto.FileModelResponse;

public interface IS3Service {

	String uploadFile(MultipartFile file);

	FileModelResponse downloadFile(String fileName);
//	FileModelResponse downloadFile(String folderPath, String fileName);

	FileModelResponse downloadFileWithPath(String websiteLogo, String folderPath);


}
