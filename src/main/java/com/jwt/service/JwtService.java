package com.jwt.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
	
	String extractUsername(String token);
	
	String generateToken(UserDetails userDetails);
	
	Boolean validateToken(String token, UserDetails userDetails);
	
	String createRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);

}
