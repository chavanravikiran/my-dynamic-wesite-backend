package com.webapp.websiteportal.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final long JWT_EXPIRATION = 86400000; // 24 hours

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey); // ✅ Decode Base64 key
        return Keys.hmacShaKeyFor(keyBytes); // ✅ Use same key for signing and validation
    }
    
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // ✅ Use the correct key
                .compact();
    }

    public String getUsernameFromToken(String token) {
    	if (token == null || !token.contains(".")) {
            throw new MalformedJwtException("Invalid JWT token format");
        }
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // ✅ Use the same key for validation
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // ✅ Use the same key for validation
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}