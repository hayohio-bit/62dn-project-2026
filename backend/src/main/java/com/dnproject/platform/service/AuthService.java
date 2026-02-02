package com.dnproject.platform.service;

import com.dnproject.platform.dto.request.LoginRequest;
import com.dnproject.platform.dto.request.SignupRequest;
import com.dnproject.platform.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    public TokenResponse login(LoginRequest request) {
        return new TokenResponse("dummy-token");
    }

    public void signup(SignupRequest request) {
    }
}
