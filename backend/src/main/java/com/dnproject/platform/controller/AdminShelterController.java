package com.dnproject.platform.controller;

import com.dnproject.platform.dto.request.ShelterSignupRequest;
import com.dnproject.platform.dto.request.ShelterVerifyRequest;
import com.dnproject.platform.dto.response.ShelterResponse;
import com.dnproject.platform.dto.response.ShelterSignupResponse;
import com.dnproject.platform.service.ShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shelters")
@RequiredArgsConstructor
public class AdminShelterController {

    private final ShelterService shelterService;

    @PostMapping("/signup")
    public ResponseEntity<ShelterSignupResponse> signup(@RequestBody ShelterSignupRequest request) {
        return ResponseEntity.ok(shelterService.registerShelter(request, 1L));
    }

    @PatchMapping("/{id}/verify")
    public ResponseEntity<ShelterResponse> verifyShelter(@PathVariable Long id,
                                                         @RequestBody ShelterVerifyRequest request) {
        return ResponseEntity.ok(shelterService.verifyShelter(id, request));
    }
}
