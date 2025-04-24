package com.webapp.websiteportal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.websiteportal.dto.ForgotPasswordRequest;
import com.webapp.websiteportal.dto.LoginRequest;
import com.webapp.websiteportal.dto.LoginResponse;
import com.webapp.websiteportal.dto.MessageResponse;
import com.webapp.websiteportal.dto.RegisterRequest;
import com.webapp.websiteportal.dto.ResetPasswordRequestNew;
import com.webapp.websiteportal.entity.Users;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.security.JwtTokenService;
import com.webapp.websiteportal.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final WebsiteDetailsRepository webSiteDetailsRepository;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        ResponseEntity<?> response = userService.registerUser(request);

        if (response.getStatusCode().is2xxSuccessful()) {
        	Optional<WebSiteDetails> websiteDetails = webSiteDetailsRepository.findByWebSiteType(request.getWebsiteType());
            if(websiteDetails.isEmpty()) {
            	return ResponseEntity.notFound().build();
            }else {
            	Users registeredUser = userService.getUserByUsernameAndWebSiteDetails(request.getUsername(),websiteDetails); // Fetch the registered user
            	String token = jwtTokenService.generateToken(registeredUser);
            	
            	Map<String, Object> responseBody = new HashMap<>();
            	responseBody.put("message", "User registered successfully");
            	responseBody.put("token", token);
            	responseBody.put("role", registeredUser.getRole()); // Assuming Users entity has getRole()
            	
            	return ResponseEntity.ok(responseBody);
            }
        }
        return response;
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
            Optional<WebSiteDetails> websiteDetails = webSiteDetailsRepository.findByWebSiteType(loginRequest.getWebsiteType());
            if(websiteDetails.isEmpty()) {
            	return ResponseEntity.notFound().build();
            }else {
            	Users user = userService.getUserByUsernameAndWebSiteDetails(loginRequest.getUsername(),websiteDetails);
            	String token = jwtTokenService.generateToken(userDetails);
            	
            	LoginResponse response = new LoginResponse();
            	
            	response.setStatus("SUCCESS");
            	response.setErrorMessage(null);
            	response.setSuccessMsg("Login successful");
            	response.setAccessToken(token);
            	response.setExpiresIn(System.currentTimeMillis() + 86400000); // same as JWT_EXPIRATION
            	response.setRefreshToken(""); // implement refresh token logic if needed
//            response.setRoles(List.of(user.getRole())); // assuming getRole() returns a String like "ADMIN"
            	response.setRoles(List.of(user.getRole().name()));
            	response.setUserName(user.getName());
            	response.setMaskMobileNumber(null); // optional: mask if needed
            	response.setUserId(user.getId()); // assuming getId() exists
            	response.setSuccess(true);
            	response.setError(false);
            	response.setWebsiteType(user.getWebSiteDetails().getWebsiteName());
            	return ResponseEntity.ok(response);
            }
            
//            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (Exception e) {
        	LoginResponse response = new LoginResponse();
            response.setStatus("FAILED");
            response.setErrorMessage("Invalid username or password");
            response.setSuccessMsg(null);
            response.setAccessToken(null);
            response.setExpiresIn(null);
            response.setRefreshToken(null);
            response.setRoles(null);
            response.setUserName(null);
            response.setMaskMobileNumber(null);
            response.setUserId(null);
            response.setSuccess(false);
            response.setError(true);

            return ResponseEntity.status(401).body(response);
//            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Invalid username or password"));
        }
    }    
    

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
    	try {
    		return userService.sendPasswordResetLink(request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(MessageResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(MessageResponse.error("An unexpected error occurred."));
        }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody ResetPasswordRequestNew request) {
    	try {
	        userService.resetPassword(request.getToken(), request.getNewPassword());
	        return ResponseEntity.ok(MessageResponse.successWithMsg("Password reset successful"));
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(MessageResponse.error(e.getMessage()));
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().body(MessageResponse.error("An unexpected error occurred."));
	    }
    }
    
//    @GetMapping("/logout")
//    public ModelAndView logout(@RequestHeader("Authorization") String token)
//            throws InvalidTokenException {
//
//        return userService.logout(token);
//    }
    
}

	
