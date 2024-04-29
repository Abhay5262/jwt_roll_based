package com.jwt.service;

import com.jwt.dto.JwtAuthenticationResponse;
import com.jwt.dto.RefreshTokenRequest;
import com.jwt.dto.SignUpRequest;
import com.jwt.dto.SigninRequest;
import com.jwt.entity.User;

public interface AuthenticationService {
	

	User signup(SignUpRequest signUpRequest);
	
	JwtAuthenticationResponse signin(SigninRequest signinRequest);
	
	JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
