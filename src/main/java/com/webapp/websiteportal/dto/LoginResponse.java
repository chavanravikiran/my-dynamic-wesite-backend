package com.webapp.websiteportal.dto;

import java.util.List;

import com.webapp.websiteportal.entity.ContactUsFeature;
import com.webapp.websiteportal.entity.OfficeType;

import lombok.Builder;
import lombok.Data;

@Data
//@Builder
public class LoginResponse {

//	public LoginResponse() {
//		// TODO Auto-generated constructor stub
//	}
	private String status;
    private String errorMessage;
    private String successMsg;
    private String accessToken;
    private Long expiresIn;
    private String refreshToken;
    private List<String> roles;
    private String userName;
    private String maskMobileNumber;
    private Long userId;
    private boolean success;
    private boolean error;
    private String websiteType;
    
}
