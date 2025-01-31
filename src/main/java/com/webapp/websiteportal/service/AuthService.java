package com.webapp.websiteportal.service;

import org.springframework.http.ResponseEntity;

import com.webapp.websiteportal.dto.OtpRequest;
import com.webapp.websiteportal.dto.OtpVerificationRequest;
import com.webapp.websiteportal.dto.ResetPasswordRequest;
import com.webapp.websiteportal.entity.Users;

public interface AuthService {
    public String generatePasswordResetToken(Users user);

    public boolean verifyPasswordResetToken(String token, Users user);

    public void deletePasswordResetToken(String token);

    public ResponseEntity<String> sendOtpForPasswordReset(OtpRequest otpRequest);

    public ResponseEntity<String> verifyOtpAndIssueResetToken(OtpVerificationRequest otpVerificationRequest);

    public ResponseEntity<String> resetPassword(ResetPasswordRequest resetPasswordRequest);

}
