package com.webapp.websiteportal.dto;

import com.webapp.websiteportal.entity.WebSiteType;

import lombok.Data;

@Data
public class RegisterRequest {
    
	private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;
    private WebSiteType websiteType;
}
