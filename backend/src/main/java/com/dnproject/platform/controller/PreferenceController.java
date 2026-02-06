package com.dnproject.platform.controller;

import com.dnproject.platform.dto.request.PreferenceRequest;
import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.PreferenceResponse;
import com.dnproject.platform.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;
    private final com.dnproject.platform.repository.UserRepository userRepository;

    @GetMapping("/api/users/me/preferences")
    public ResponseEntity<ApiResponse<PreferenceResponse>> getMyPreference(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        PreferenceResponse response = preferenceService.getPreference(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/api/users/me/preferences")
    public ResponseEntity<ApiResponse<PreferenceResponse>> updateMyPreference(
            @RequestBody PreferenceRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        PreferenceResponse response = preferenceService.savePreference(userId, request);
        return ResponseEntity.ok(ApiResponse.success("선호도가 저장되었습니다.", response));
    }

    @GetMapping("/api/preferences")
    public ResponseEntity<ApiResponse<PreferenceResponse>> getPreference(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        PreferenceResponse response = preferenceService.getPreference(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/api/preferences")
    public ResponseEntity<ApiResponse<PreferenceResponse>> savePreference(
            @RequestBody PreferenceRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        PreferenceResponse response = preferenceService.savePreference(userId, request);
        return ResponseEntity.ok(ApiResponse.success("선호도가 저장되었습니다.", response));
    }

    private Long getUserId(UserDetails userDetails) {
        if (userDetails == null) {
            return 1L;
        }
        return userRepository.findByEmail(userDetails.getUsername())
                .map(user -> user.getId())
                .orElse(1L);
    }
}
