package com.webapp.websiteportal.Schedulers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.webapp.websiteportal.repository.PasswordResetTokenNewRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduledTaskService {

	@Autowired
	private PasswordResetTokenNewRepository tokenRepository;
	
//  Runs every 10 minutes
//	@Scheduled(cron = "0 */5 * * * *") // every 5 minutes
	@Scheduled(fixedRate = 5 * 60 * 1000) // every 5 minutes
    public void cleanupExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        tokenRepository.deleteByExpiryDateBefore(now);
        System.out.println("Expired tokens cleaned up at: " + now);
    }
}
