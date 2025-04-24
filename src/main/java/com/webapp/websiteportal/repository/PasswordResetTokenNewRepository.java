package com.webapp.websiteportal.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webapp.websiteportal.entity.PasswordResetTokenNew;
import com.webapp.websiteportal.entity.Users;

@Repository
public interface PasswordResetTokenNewRepository extends JpaRepository<PasswordResetTokenNew, Long> {
    
	Optional<PasswordResetTokenNew> findByToken(String token);

	Optional<PasswordResetTokenNew> findByUser(Users users);
	
	List<PasswordResetTokenNew> findByExpiryDateBefore(LocalDateTime now);
    
	void deleteByExpiryDateBefore(LocalDateTime now);
	
}