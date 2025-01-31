package com.webapp.websiteportal.service;

import com.webapp.websiteportal.dto.AccountResponse;
import com.webapp.websiteportal.dto.UserResponse;

public interface DashboardService {
    UserResponse getUserDetails(String accountNumber);
    AccountResponse getAccountDetails(String accountNumber);
}