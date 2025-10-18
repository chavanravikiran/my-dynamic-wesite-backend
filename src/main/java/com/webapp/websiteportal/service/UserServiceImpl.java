//package com.webapp.websiteportal.service;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.webapp.websiteportal.dto.MessageResponse;
//import com.webapp.websiteportal.dto.RegisterRequest;
//import com.webapp.websiteportal.entity.PasswordResetTokenNew;
//import com.webapp.websiteportal.entity.Users;
//import com.webapp.websiteportal.entity.WebSiteDetails;
//import com.webapp.websiteportal.entity.WebSiteType;
//import com.webapp.websiteportal.repository.PasswordResetTokenNewRepository;
//import com.webapp.websiteportal.repository.UserRepository;
//import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserDetailsService { // ✅ Implements UserDetailsService
//	
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    
//    @Autowired
//    private PasswordResetTokenNewRepository tokenRepository;
//
//    @Autowired
//    private WebsiteDetailsRepository websiteDetailsRepository;
//    
//    @Autowired
//    private JavaMailSender mailSender;
//    
//    @Value("${application.selfUrl}")
//    private String selfUrl;
//    
//    public ResponseEntity<?> registerUser(RegisterRequest request) {
//    	try {
//    		if (userRepository.findByUsername(request.getUsername()).isPresent()) {
//    			return ResponseEntity.badRequest().body("Username already exists");
//    		}
//    		
//    		Users user = new Users();
//    		user.setName(request.getName());
//    		user.setUsername(request.getUsername());
//    		user.setEmail(request.getEmail());
//    		user.setPhoneNumber(request.getPhoneNumber());
//    		user.setAddress(request.getAddress());
//    		user.setCountryCode("IND");
//    		user.setPassword(passwordEncoder.encode(request.getPassword()));//$2a$10$764UuV07AOzJPZOdpSwrb.NeWIb365tcYJQ.Zvl1bXKTxtOaMhDp2  -->Admin@123
//    		user.setWebSiteDetails(websiteDetailsRepository.findByWebSiteTypeAndIsActive(request.getWebsiteType(), 'Y'));
//    		userRepository.save(user);
//    		return ResponseEntity.ok("User registered successfully");
//    	}catch (Exception e) {
//    		log.error("Error registering user: {}", e.getMessage(), e);
//            return ResponseEntity.internalServerError().body("An unexpected error occurred while registering the user.");
//		}
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//    }
//
//    public Users getUserByUsernameAndWebSiteDetails(String username,Optional<WebSiteDetails> websiteDetails) {
//        return userRepository.findByUsernameAndWebSiteDetails(username,websiteDetails).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//    }
//
//    public ResponseEntity<MessageResponse> sendPasswordResetLink(String email) {
//        Optional<Users> userOpt = userRepository.findByEmail(email);
//        if (userOpt.isEmpty()) {
//            return ResponseEntity.badRequest().body(MessageResponse.error("Email not found"));
//        }
//
//        Users user = userOpt.get();
//        Optional<PasswordResetTokenNew> tokenExist = tokenRepository.findByUser(user);
//
//        if (tokenExist.isPresent()) {
//            return ResponseEntity.badRequest().body(MessageResponse.error("Reset link already sent to your email:"+email));
//        }
//
//        String token = UUID.randomUUID().toString();
//        PasswordResetTokenNew resetToken = new PasswordResetTokenNew();
//        resetToken.setToken(token);
//        resetToken.setUser(user);
//        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
//        tokenRepository.save(resetToken);
//
//        String resetUrl = selfUrl+"/reset-password?token=" + token;
//
//        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setTo(user.getEmail());
//        mail.setSubject("Reset Your Password");
//        mail.setText("Click the link below to reset your password:\n" + resetUrl + "\nThis link will expire in 30 minutes.");
//        mailSender.send(mail);
//
//        return ResponseEntity.ok(MessageResponse.successWithMsg("Password reset link sent to your email"));
//    }
//    
//    public void resetPassword(String token, String newPassword) {
//        PasswordResetTokenNew resetToken = tokenRepository.findByToken(token)
//            .orElseThrow(() -> new RuntimeException("Invalid token"));
//
//        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
//            throw new RuntimeException("Token expired");
//        }
//
//        Users user = resetToken.getUser();
//
//        if (passwordEncoder.matches(newPassword, user.getPassword())) {
//            throw new RuntimeException("New password cannot be same as old password");
//        }
//
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//        tokenRepository.delete(resetToken);
//    }
//    
//}
package com.webapp.websiteportal.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webapp.websiteportal.dto.MessageResponse;
import com.webapp.websiteportal.dto.RegisterRequest;
import com.webapp.websiteportal.entity.PasswordResetTokenNew;
import com.webapp.websiteportal.entity.Users;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.repository.PasswordResetTokenNewRepository;
import com.webapp.websiteportal.repository.UserRepository;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // ✅ From AppConfig (no circular ref)

    @Autowired
    private PasswordResetTokenNewRepository tokenRepository;

    @Autowired
    private WebsiteDetailsRepository websiteDetailsRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${application.selfUrl}")
    private String selfUrl;

    public ResponseEntity<?> registerUser(RegisterRequest request) {
        try {
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Username already exists");
            }

            Users user = new Users();
            user.setName(request.getName());
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setAddress(request.getAddress());
            user.setCountryCode("IND");
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setWebSiteDetails(websiteDetailsRepository.findByWebSiteTypeAndIsActive(request.getWebsiteType(), 'Y'));

            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully");

        } catch (Exception e) {
            log.error("Error registering user: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("An unexpected error occurred while registering the user.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public Users getUserByUsernameAndWebSiteDetails(String username, Optional<WebSiteDetails> websiteDetails) {
        return userRepository.findByUsernameAndWebSiteDetails(username, websiteDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public ResponseEntity<MessageResponse> sendPasswordResetLink(String email) {
        Optional<Users> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(MessageResponse.error("Email not found"));
        }

        Users user = userOpt.get();
        Optional<PasswordResetTokenNew> tokenExist = tokenRepository.findByUser(user);

        if (tokenExist.isPresent()) {
            return ResponseEntity.badRequest().body(MessageResponse.error("Reset link already sent to your email: " + email));
        }

        String token = UUID.randomUUID().toString();
        PasswordResetTokenNew resetToken = new PasswordResetTokenNew();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        tokenRepository.save(resetToken);

        String resetUrl = selfUrl + "/reset-password?token=" + token;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setSubject("Reset Your Password");
        mail.setText("Click the link below to reset your password:\n" + resetUrl + "\nThis link will expire in 30 minutes.");
        mailSender.send(mail);

        return ResponseEntity.ok(MessageResponse.successWithMsg("Password reset link sent to your email"));
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetTokenNew resetToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        Users user = resetToken.getUser();

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("New password cannot be same as old password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
    }
}

