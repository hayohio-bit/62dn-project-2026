package com.antigravity.adoption.controller;

import com.antigravity.adoption.dto.AdoptionApplyRequestDTO;
import com.antigravity.adoption.dto.AdoptionResponseDTO;
import com.antigravity.adoption.entity.AdoptionStatus;
import com.antigravity.adoption.service.AdoptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Adoption", description = "Adoption Management API")
@RestController
@RequestMapping("/api/adoptions")
@RequiredArgsConstructor
public class AdoptionController {

    private final AdoptionService adoptionService;

    @Operation(summary = "입양 신청")
    @PostMapping
    public ResponseEntity<AdoptionResponseDTO> apply(@Valid @RequestBody AdoptionApplyRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adoptionService.apply(requestDTO));
    }

    @Operation(summary = "입양 신청 목록 조회")
    @GetMapping
    public ResponseEntity<Page<AdoptionResponseDTO>> list(
            @RequestParam(required = false) AdoptionStatus status,
            Pageable pageable) {
        return ResponseEntity.ok(adoptionService.getRequests(status, pageable));
    }

    @Operation(summary = "입양 신청 승인")
    @PostMapping("/{id}/approve")
    public ResponseEntity<AdoptionResponseDTO> approve(@PathVariable Long id) {
        return ResponseEntity.ok(adoptionService.approve(id));
    }

    @Operation(summary = "입양 신청 거절")
    @PostMapping("/{id}/reject")
    public ResponseEntity<AdoptionResponseDTO> reject(@PathVariable Long id) {
        return ResponseEntity.ok(adoptionService.reject(id));
    }

    @Operation(summary = "입양 신청 취소")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        adoptionService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
