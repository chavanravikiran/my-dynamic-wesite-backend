package com.webapp.websiteportal.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.websiteportal.dto.LoginRequest;
import com.webapp.websiteportal.dto.OtpRequest;
import com.webapp.websiteportal.dto.OtpVerificationRequest;
import com.webapp.websiteportal.entity.Users;
import com.webapp.websiteportal.exception.InvalidTokenException;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    public ResponseEntity<String> registerUser(Users user);

    public ResponseEntity<String> login(LoginRequest loginRequest, HttpServletRequest request)
            throws InvalidTokenException;

    public ResponseEntity<String> generateOtp(OtpRequest otpRequest);

    public ResponseEntity<String> verifyOtpAndLogin(OtpVerificationRequest otpVerificationRequest)
            throws InvalidTokenException;

    public ResponseEntity<String> updateUser(Users user);

    public ModelAndView logout(String token) throws InvalidTokenException;

    public boolean resetPassword(Users user, String newPassword);

    public Users saveUser(Users user);

    public Users getUserByIdentifier(String identifier);

    public Users getUserByAccountNumber(String accountNo);

    public Users getUserByEmail(String email);

}
