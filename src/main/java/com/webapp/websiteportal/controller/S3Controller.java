package com.webapp.websiteportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webapp.websiteportal.dto.FileModelResponse;
import com.webapp.websiteportal.service.IS3Service;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {
	
	@Autowired
	private IS3Service s3Service;
	
	@PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return s3Service.uploadFile(file);
    }
	
	@GetMapping("/download")
    public ResponseEntity<FileModelResponse> downloadFile(@RequestParam("fileName") String fileName) {
        FileModelResponse response = s3Service.downloadFile(fileName);

        return ResponseEntity.ok(response);
    }
}
