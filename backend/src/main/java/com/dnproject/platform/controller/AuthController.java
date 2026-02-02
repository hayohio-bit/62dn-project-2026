package com.dnproject.platform.controller;

import com.dnproject.platform.dto.request.LoginRequest;
import com.dnproject.platform.dto.request.SignupRequest;
import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody LoginRequest request) {
        return ApiResponse.success(new TokenResponse("dummy-token"));
    }

    @PostMapping("/signup")
    public ApiResponse<Void> signup(@RequestBody SignupRequest request) {
        return ApiResponse.success(null);
    }
}
