package com.webapp.websiteportal.dto;

import lombok.Data;

@Data
public class ResetPasswordRequestNew {
	private String token;
    private String newPassword;
}