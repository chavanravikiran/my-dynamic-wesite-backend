package com.webapp.websiteportal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.webapp.websiteportal.service.EmailService;
import com.webapp.websiteportal.service.EmailServiceImpl;

@Configuration
public class TestConfig {

    @Bean
    @Primary
    public EmailService emailService() {
        return new EmailServiceImpl(new GreenMailJavaMailSender());
    }
}
