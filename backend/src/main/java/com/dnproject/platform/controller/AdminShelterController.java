package com.dnproject.platform.controller;

import com.dnproject.platform.dto.response.AdminShelterResponse;
import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.PageResponse;
import com.dnproject.platform.service.AdminShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/shelters")
@RequiredArgsConstructor
public class AdminShelterController {

    private final AdminShelterService adminShelterService;

    @GetMapping("/requests")
    public ResponseEntity<ApiResponse<PageResponse<AdminShelterResponse>>> getVerificationRequests(Pageable pageable) {
        Page<AdminShelterResponse> response = adminShelterService.getVerificationRequests(pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(response)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AdminShelterResponse>>> getAllShelters(Pageable pageable) {
        Page<AdminShelterResponse> response = adminShelterService.getAllShelters(pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(response)));
    }
}
