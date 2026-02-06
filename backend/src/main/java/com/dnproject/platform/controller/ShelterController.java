package com.dnproject.platform.controller;

import com.dnproject.platform.dto.request.ShelterSignupRequest;
import com.dnproject.platform.dto.response.ShelterResponse;
import com.dnproject.platform.dto.response.ShelterSignupResponse;
import com.dnproject.platform.service.ShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/shelters")
@RequiredArgsConstructor
public class ShelterController {

    private final ShelterService shelterService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody ShelterSignupRequest request) {
        ShelterSignupResponse response = shelterService.registerShelter(request);
        return ResponseEntity.ok(Map.of("data", response));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getShelters(Pageable pageable) {
        Page<ShelterResponse> shelters = shelterService.getShelters(pageable);
        return ResponseEntity.ok(Map.of("data", Map.of(
                "content", shelters.getContent(),
                "totalPages", shelters.getTotalPages(),
                "totalElements", shelters.getTotalElements())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getShelter(@PathVariable Long id) {
        ShelterResponse response = shelterService.getShelter(id);
        return ResponseEntity.ok(Map.of("data", response));
    }
}
