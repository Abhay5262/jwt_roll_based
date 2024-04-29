package com.jwt.serviceImpl;


import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.dto.JwtAuthenticationResponse;
import com.jwt.dto.RefreshTokenRequest;
import com.jwt.dto.SignUpRequest;
import com.jwt.dto.SigninRequest;
import com.jwt.entity.Role;
import com.jwt.entity.User;
import com.jwt.repository.UserRepo;
import com.jwt.service.AuthenticationService;
import com.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.var;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
	
	private final UserRepo userRepo;
	
	private PasswordEncoder passwordEncoder;
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtService jwtService;
	
	public User signup(SignUpRequest signUpRequest) {
		User user = new User();
		
		user.setEmail(signUpRequest.getEmail());
		user.setFirstname(signUpRequest.getFirstName());
		user.setSecondname(signUpRequest.getLastName());
		user.setRole(Role.USER);
//		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setPassword(signUpRequest.getPassword());
		
		return userRepo.save(user);
	}
	
	public JwtAuthenticationResponse signin(SigninRequest signinRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
		
		var user = userRepo.findByEmail(signinRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password."));
		var jwt = jwtService.generateToken(user);
		var refreshToken = jwtService.createRefreshToken(new HashMap<>(), user);
		
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
		
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		
		return jwtAuthenticationResponse;
	}
	
	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
		User user = userRepo.findByEmail(userEmail).orElseThrow();
		
		if(jwtService.validateToken(refreshTokenRequest.getToken(), user)) {
			
			var jwt = jwtService.generateToken(user);
			
			JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
			
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
			
			return jwtAuthenticationResponse;
		}
		return null;
	}

}
