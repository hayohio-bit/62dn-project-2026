package com.dnproject.platform.controller;

import com.dnproject.platform.dto.request.AdoptionRequest;
import com.dnproject.platform.dto.response.AdoptionResponse;
import com.dnproject.platform.service.AdoptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/adoptions")
@RequiredArgsConstructor
public class AdoptionController {

    private final AdoptionService adoptionService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> apply(@RequestBody AdoptionRequest request) {
        // 실제 운영에선 SecurityContextHolder에서 userId를 가져와야 함. 일단 1번 유저로 가정하거나 파라미터로 받음.
        // 프론트엔드 요청 구조에 맞춰 구현.
        Long userId = 1L;
        AdoptionResponse response = adoptionService.apply(request, userId);
        return ResponseEntity.ok(Map.of("data", response));
    }

    @GetMapping("/my")
    public ResponseEntity<Map<String, Object>> getMyList(Pageable pageable) {
        Long userId = 1L;
        Page<AdoptionResponse> list = adoptionService.getMyList(userId, pageable);
        return ResponseEntity.ok(Map.of(
                "data", Map.of(
                        "content", list.getContent(),
                        "totalPages", list.getTotalPages(),
                        "totalElements", list.getTotalElements())));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Map<String, Object>> cancel(@PathVariable Long id) {
        Long userId = 1L;
        AdoptionResponse response = adoptionService.cancel(id, userId);
        return ResponseEntity.ok(Map.of("data", response));
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<Map<String, Object>> getByAnimalId(@PathVariable Long animalId, Pageable pageable) {
        Page<AdoptionResponse> list = adoptionService.getByAnimalId(animalId, pageable);
        return ResponseEntity.ok(Map.of("data", list));
    }

    @GetMapping("/shelter/pending")
    public ResponseEntity<Map<String, Object>> getPendingByShelter(@RequestParam Long shelterId, Pageable pageable) {
        Page<AdoptionResponse> list = adoptionService.getPendingByShelter(shelterId, pageable);
        return ResponseEntity.ok(Map.of("data", list));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Map<String, Object>> approve(@PathVariable Long id) {
        AdoptionResponse response = adoptionService.approve(id);
        return ResponseEntity.ok(Map.of("data", response));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Map<String, Object>> reject(@PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body) {
        String rejectReason = (body != null) ? body.get("rejectReason") : null;
        AdoptionResponse response = adoptionService.reject(id, rejectReason);
        return ResponseEntity.ok(Map.of("data", response));
    }
}
