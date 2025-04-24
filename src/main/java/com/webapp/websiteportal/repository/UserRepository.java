package com.webapp.websiteportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.Users;
import com.webapp.websiteportal.entity.WebSiteDetails;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

//    UserDetails findByEmail(String email);

    Optional<Users> findByPhoneNumber(String phoneNumber);

    Optional<Users> findByAccountAccountNumber(String accountNumber);
    
    Optional<Users> findByUsername(String username);
    
    Optional<Users> findByUsernameAndWebSiteDetails(String username,Optional<WebSiteDetails> websiteDetails);
    
    Optional<Users> findByEmail(String email);
    
}
