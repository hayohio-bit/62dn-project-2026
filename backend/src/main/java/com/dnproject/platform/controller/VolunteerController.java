package com.dnproject.platform.controller;

import com.dnproject.platform.domain.constant.VolunteerStatus;
import com.dnproject.platform.dto.request.VolunteerApplyRequest;
import com.dnproject.platform.dto.request.VolunteerRecruitmentCreateRequest;
import com.dnproject.platform.dto.response.VolunteerRecruitmentResponse;
import com.dnproject.platform.dto.response.VolunteerResponse;
import com.dnproject.platform.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/volunteers")
@RequiredArgsConstructor
public class VolunteerController {

    private final VolunteerService volunteerService;
    private final com.dnproject.platform.repository.UserRepository userRepository;

    // 봉사활동 모집 생성
    @PostMapping("/recruit")
    public ResponseEntity<Map<String, Object>> recruit(@RequestBody VolunteerRecruitmentCreateRequest request) {
        VolunteerRecruitmentResponse response = volunteerService.createRecruitment(request);
        return ResponseEntity.ok(Map.of("status", 200, "message", "모집공고 생성 완료", "data", response));
    }

    // 봉사활동 신청 (Frontend 경로: POST /volunteers)
    @PostMapping
    public ResponseEntity<Map<String, Object>> apply(
            @RequestBody VolunteerApplyRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        VolunteerResponse response = volunteerService.applyVolunteer(userId, request);
        return ResponseEntity.ok(Map.of("status", 200, "message", "봉사활동 신청 완료", "data", response));
    }

    // 기존 /apply 경로도 유지 (하위 호환)
    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> applyLegacy(
            @RequestBody VolunteerApplyRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        VolunteerResponse response = volunteerService.applyVolunteer(userId, request);
        return ResponseEntity.ok(Map.of("status", 200, "message", "봉사활동 신청 완료", "data", response));
    }

    // 모집공고 목록 조회
    @GetMapping("/recruitments")
    public ResponseEntity<Map<String, Object>> getRecruitments(Pageable pageable) {
        Page<VolunteerRecruitmentResponse> response = volunteerService.getRecruitments(pageable);
        return ResponseEntity.ok(Map.of("status", 200, "message", "성공", "data", toPageResponse(response)));
    }

    // 내 봉사활동 목록 조회
    @GetMapping("/my")
    public ResponseEntity<Map<String, Object>> getMyVolunteers(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable) {
        Long userId = getUserId(userDetails);
        Page<VolunteerResponse> response = volunteerService.getMyVolunteers(userId, pageable);
        return ResponseEntity.ok(Map.of("status", 200, "message", "성공", "data", toPageResponse(response)));
    }

    // 봉사활동 상태 변경 (PUT 지원)
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateStatusPut(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        VolunteerStatus status = VolunteerStatus.valueOf(body.get("status"));
        VolunteerResponse response = volunteerService.updateApplicationStatus(id, status);
        return ResponseEntity.ok(Map.of("status", 200, "message", "상태 변경 완료", "data", response));
    }

    // 기존 PATCH 경로 유지 (하위 호환)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateStatus(
            @PathVariable Long id,
            @RequestParam VolunteerStatus status) {
        VolunteerResponse response = volunteerService.updateApplicationStatus(id, status);
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

    private Map<String, Object> toPageResponse(Page<?> page) {
        return Map.of(
                "content", page.getContent(),
                "page", page.getNumber(),
                "size", page.getSize(),
                "totalElements", page.getTotalElements(),
                "totalPages", page.getTotalPages());
    }
}
