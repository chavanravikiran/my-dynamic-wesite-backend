package com.webapp.websiteportal.dto;

public record ResetPasswordRequest(String identifier, String resetToken, String newPassword) {
}
