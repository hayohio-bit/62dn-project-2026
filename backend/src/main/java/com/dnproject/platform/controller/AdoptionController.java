package com.dnproject.platform.controller;

import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.dto.request.AdoptionRequest;
import com.dnproject.platform.dto.response.AdoptionResponse;
import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.PageResponse;
import com.dnproject.platform.service.AdoptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/adoptions")
@RequiredArgsConstructor
public class AdoptionController {

    private final AdoptionService adoptionService;
    private final com.dnproject.platform.repository.UserRepository userRepository;
    private final com.dnproject.platform.repository.ShelterRepository shelterRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<AdoptionResponse>> apply(
            @RequestBody AdoptionRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        AdoptionResponse response = adoptionService.apply(request, userId);
        return ResponseEntity.ok(ApiResponse.success("입양 신청이 완료되었습니다.", response));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<PageResponse<AdoptionResponse>>> getMyAdoptions(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable) {
        Long userId = getUserId(userDetails);
        Page<AdoptionResponse> response = adoptionService.getMyList(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(response)));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<AdoptionResponse>> cancel(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        AdoptionResponse response = adoptionService.cancel(id, userId);
        return ResponseEntity.ok(ApiResponse.success("입양 신청이 취소되었습니다.", response));
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<ApiResponse<PageResponse<AdoptionResponse>>> getByAnimalId(
            @PathVariable Long animalId,
            Pageable pageable) {
        Page<AdoptionResponse> response = adoptionService.getByAnimalId(animalId, pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(response)));
    }

    @GetMapping("/shelter/pending")
    public ResponseEntity<ApiResponse<PageResponse<AdoptionResponse>>> getPendingByShelter(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable) {
        Long shelterId = getShelterId(userDetails);
        Page<AdoptionResponse> response = adoptionService.getPendingByShelter(shelterId, pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(response)));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<AdoptionResponse>> approve(@PathVariable Long id) {
        AdoptionResponse response = adoptionService.approve(id);
        return ResponseEntity.ok(ApiResponse.success("입양 신청이 승인되었습니다.", response));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<AdoptionResponse>> reject(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body) {
        String rejectReason = body != null ? body.get("rejectReason") : null;
        AdoptionResponse response = adoptionService.reject(id, rejectReason);
        return ResponseEntity.ok(ApiResponse.success("입양 신청이 반려되었습니다.", response));
    }

    private Long getUserId(UserDetails userDetails) {
        if (userDetails == null) {
            return 1L; // 임시: 테스트용
        }
        return userRepository.findByEmail(userDetails.getUsername())
                .map(user -> user.getId())
                .orElse(1L);
    }

    private Long getShelterId(UserDetails userDetails) {
        if (userDetails == null) {
            return 1L; // 임시: 테스트용
        }
        return userRepository.findByEmail(userDetails.getUsername())
                .flatMap(user -> shelterRepository.findByManagerId(user.getId()))
                .map(Shelter::getId)
                .orElse(1L);
    }
}
