package com.dnproject.platform.controller;

import com.dnproject.platform.domain.constant.AdoptionStatus;
import com.dnproject.platform.domain.constant.VolunteerStatus;
import com.dnproject.platform.dto.response.AdminApplicationResponse;
import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.PageResponse;
import com.dnproject.platform.service.AdminApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/applications")
@RequiredArgsConstructor
public class AdminApplicationController {

    private final AdminApplicationService adminApplicationService;

    @GetMapping("/adoptions")
    public ResponseEntity<ApiResponse<PageResponse<AdminApplicationResponse>>> getAdoptionApplications(
            Pageable pageable) {
        Page<AdminApplicationResponse> response = adminApplicationService.getAdoptionApplications(pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(response)));
    }

    @PatchMapping("/adoptions/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateAdoptionStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        AdoptionStatus status = AdoptionStatus.valueOf(body.get("status"));
        adminApplicationService.updateAdoptionStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("입양 신청 상태가 변경되었습니다.", null));
    }

    @PutMapping("/adoptions/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateAdoptionStatusPut(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        AdoptionStatus status = AdoptionStatus.valueOf(body.get("status"));
        adminApplicationService.updateAdoptionStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("입양 신청 상태가 변경되었습니다.", null));
    }

    @GetMapping("/volunteers")
    public ResponseEntity<ApiResponse<PageResponse<AdminApplicationResponse>>> getVolunteerApplications(
            Pageable pageable) {
        Page<AdminApplicationResponse> response = adminApplicationService.getVolunteerApplications(pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(response)));
    }

    @PatchMapping("/volunteers/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateVolunteerStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        VolunteerStatus status = VolunteerStatus.valueOf(body.get("status"));
        adminApplicationService.updateVolunteerStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("봉사 신청 상태가 변경되었습니다.", null));
    }

    @PutMapping("/volunteers/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateVolunteerStatusPut(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        VolunteerStatus status = VolunteerStatus.valueOf(body.get("status"));
        adminApplicationService.updateVolunteerStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("봉사 신청 상태가 변경되었습니다.", null));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        Map<String, Object> response = adminApplicationService.getApplicationStats();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
