package com.webapp.websiteportal.dto;

import com.webapp.websiteportal.entity.WebSiteType;

import lombok.Data;

@Data
public class InquiryRequest {

	private String firstName;
	private String lastName;
    private String email;
    private String phone;
	private String title;
	private String message;
	private WebSiteType websiteType;
	
}