package com.webapp.websiteportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.PasswordResetToken_old;
import com.webapp.websiteportal.entity.Users;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken_old, Long> {
    Optional<PasswordResetToken_old> findByToken(String token);

    PasswordResetToken_old findByUser(Users user);

    void deleteByToken(String token);
}
