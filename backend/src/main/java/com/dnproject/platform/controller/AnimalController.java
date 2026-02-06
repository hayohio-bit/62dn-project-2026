package com.dnproject.platform.controller;

import com.dnproject.platform.domain.constant.AnimalStatus;
import com.dnproject.platform.dto.request.AnimalCreateRequest;
import com.dnproject.platform.dto.response.AnimalResponse;
import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.PageResponse;
import com.dnproject.platform.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final com.dnproject.platform.repository.UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AnimalResponse>>> getAnimals(
            @RequestParam(required = false) AnimalStatus status,
            @RequestParam(required = false) String species,
            @RequestParam(required = false, name = "animalSize") String animalSize,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String sigungu,
            @RequestParam(required = false) String search,
            Pageable pageable) {
        Page<AnimalResponse> animals = animalService.getAnimals(status, species, animalSize, region, sigungu, search,
                pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(animals)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AnimalResponse>> getAnimal(@PathVariable Long id) {
        AnimalResponse animal = animalService.getAnimal(id);
        return ResponseEntity.ok(ApiResponse.success(animal));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AnimalResponse>> createAnimal(@RequestBody AnimalCreateRequest request) {
        AnimalResponse animal = animalService.createAnimal(request);
        return ResponseEntity.ok(ApiResponse.success("동물 정보가 등록되었습니다.", animal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AnimalResponse>> updateAnimal(@PathVariable Long id,
            @RequestBody AnimalCreateRequest request) {
        AnimalResponse animal = animalService.updateAnimal(id, request);
        return ResponseEntity.ok(ApiResponse.success("동물 정보가 수정되었습니다.", animal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok(ApiResponse.success("동물 정보가 삭제되었습니다.", null));
    }

    @GetMapping("/recommendations")
    public ResponseEntity<ApiResponse<PageResponse<AnimalResponse>>> getRecommendations(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable) {
        Long userId = getUserId(userDetails);
        Page<AnimalResponse> animals = animalService.getRecommendations(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success("추천 동물 목록 조회 성공", PageResponse.from(animals)));
    }

    private Long getUserId(UserDetails userDetails) {
        if (userDetails == null) {
            return 1L; // 임시: 테스트용
        }
        return userRepository.findByEmail(userDetails.getUsername())
                .map(user -> user.getId())
                .orElse(1L);
    }
}
