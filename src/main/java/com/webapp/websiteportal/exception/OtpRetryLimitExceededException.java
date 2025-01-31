package com.webapp.websiteportal.exception;

public class OtpRetryLimitExceededException extends RuntimeException {

    public OtpRetryLimitExceededException(String message) {
        super(message);
    }
}
