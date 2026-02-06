package com.dnproject.platform.controller;

import com.dnproject.platform.dto.request.LoginRequest;
import com.dnproject.platform.dto.request.ShelterSignupRequest;
import com.dnproject.platform.dto.request.SignupRequest;
import com.dnproject.platform.dto.request.UpdateMeRequest;
import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.ShelterSignupResponse;
import com.dnproject.platform.dto.response.TokenResponse;
import com.dnproject.platform.dto.response.UserResponse;
import com.dnproject.platform.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>> signup(@Valid @RequestBody SignupRequest request) {
        UserResponse response = authService.signup(request);
        return ResponseEntity.ok(ApiResponse.success("회원가입이 완료되었습니다.", response));
    }

    @PostMapping("/shelter-signup")
    public ResponseEntity<ApiResponse<ShelterSignupResponse>> shelterSignup(
            @Valid @ModelAttribute ShelterSignupRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ShelterSignupResponse response = authService.shelterSignup(request, file);
        return ResponseEntity.ok(ApiResponse.success("보호소 회원가입 신청이 완료되었습니다.", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("로그인 성공", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@AuthenticationPrincipal UserDetails userDetails) {
        // Simple success response (JWT client-side handle)
        return ResponseEntity.ok(ApiResponse.success("로그아웃 되었습니다.", null));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refresh(@RequestParam String refreshToken) {
        TokenResponse response = authService.refresh(refreshToken);
        return ResponseEntity.ok(ApiResponse.success("토큰 재발급 성공", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        UserResponse response = authService.getMe(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateMe(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateMeRequest request) {
        UserResponse response = authService.updateMe(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success("내 정보가 수정되었습니다.", response));
    }
}
