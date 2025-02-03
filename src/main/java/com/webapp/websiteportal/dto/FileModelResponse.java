package com.webapp.websiteportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileModelResponse {
	 
	private String base64;
    private String status;
    private String errorMessage;
    private String successMessage;
    private String fileType;
    private String mimeType;
    
}
