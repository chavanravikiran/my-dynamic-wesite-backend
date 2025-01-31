package com.webapp.websiteportal.dto;

public record PinUpdateRequest(String accountNumber, String oldPin, String newPin, String password) {
}
