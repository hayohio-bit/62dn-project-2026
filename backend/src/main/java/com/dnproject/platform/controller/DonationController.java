package com.dnproject.platform.controller;

import com.dnproject.platform.domain.constant.DonationStatus;
import com.dnproject.platform.dto.request.DonationApplyRequest;
import com.dnproject.platform.dto.request.DonationRequestCreateRequest;
import com.dnproject.platform.dto.response.DonationRequestResponse;
import com.dnproject.platform.dto.response.DonationResponse;
import com.dnproject.platform.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;
    private final com.dnproject.platform.repository.UserRepository userRepository;

    // 후원 신청 (Frontend: POST /donations)
    @PostMapping
    public ResponseEntity<Map<String, Object>> apply(
            @RequestBody DonationApplyRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        DonationResponse response = donationService.applyDonation(request);
        return ResponseEntity.ok(Map.of("status", 200, "message", "후원 신청 완료", "data", response));
    }

    // 기존 /apply 경로 유지 (하위 호환)
    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> applyDonation(@RequestBody DonationApplyRequest request) {
        DonationResponse response = donationService.applyDonation(request);
        return ResponseEntity.ok(Map.of("status", 200, "message", "후원 신청 완료", "data", response));
    }

    // 후원 요청 생성
    @PostMapping("/request")
    public ResponseEntity<Map<String, Object>> createRequest(@RequestBody DonationRequestCreateRequest request) {
        DonationRequestResponse response = donationService.createDonationRequest(request);
        return ResponseEntity.ok(Map.of("status", 200, "message", "후원 요청 생성 완료", "data", response));
    }

    // 내 후원 목록 조회 (Frontend: GET /donations/my)
    @GetMapping("/my")
    public ResponseEntity<Map<String, Object>> getMyDonations(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        List<DonationResponse> responses = donationService.getMyDonations(userId);
        return ResponseEntity.ok(Map.of("status", 200, "message", "성공", "data", responses));
    }

    // 보호소별 후원 목록 조회
    @GetMapping("/shelter/{shelterId}")
    public ResponseEntity<Map<String, Object>> getShelterDonations(@PathVariable Long shelterId) {
        List<DonationResponse> responses = donationService.getShelterDonations(shelterId);
        return ResponseEntity.ok(Map.of("status", 200, "message", "성공", "data", responses));
    }

    // 후원 상태 변경 (PUT 지원)
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateStatusPut(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        DonationStatus status = DonationStatus.valueOf(body.get("status"));
        DonationResponse response = donationService.updateDonationStatus(id, status);
        return ResponseEntity.ok(Map.of("status", 200, "message", "상태 변경 완료", "data", response));
    }

    // 기존 PATCH 경로 유지 (하위 호환)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateStatus(
            @PathVariable Long id,
            @RequestParam DonationStatus status) {
        DonationResponse response = donationService.updateDonationStatus(id, status);
        return ResponseEntity.ok(Map.of("status", 200, "message", "상태 변경 완료", "data", response));
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
