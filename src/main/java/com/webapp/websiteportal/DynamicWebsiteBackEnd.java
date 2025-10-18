package com.webapp.websiteportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching // Add this annotation to enable caching support
@EnableAsync
@EnableScheduling // Enable scheduling support 
public class DynamicWebsiteBackEnd{

	public static void main(String[] args) {
		SpringApplication.run(DynamicWebsiteBackEnd.class, args);
	}

}