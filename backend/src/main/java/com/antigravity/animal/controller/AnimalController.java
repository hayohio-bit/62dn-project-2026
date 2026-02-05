package com.antigravity.animal.controller;

import com.antigravity.animal.dto.AnimalRequestDTO;
import com.antigravity.animal.dto.AnimalResponseDTO;
import com.antigravity.animal.entity.AnimalStatus;
import com.antigravity.animal.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Animal", description = "Animal Management API")
@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @Operation(summary = "동물 등록")
    @PostMapping
    public ResponseEntity<AnimalResponseDTO> register(@Valid @RequestBody AnimalRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.registerAnimal(requestDTO));
    }

    @Operation(summary = "동물 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getAnimal(id));
    }

    @Operation(summary = "동물 목록 조회 (상태 필터링 및 페이징)")
    @GetMapping
    public ResponseEntity<Page<AnimalResponseDTO>> list(
            @RequestParam(required = false) AnimalStatus status,
            Pageable pageable) {
        return ResponseEntity.ok(animalService.getAnimals(status, pageable));
    }

    @Operation(summary = "동물 정보 수정")
    @PutMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody AnimalRequestDTO requestDTO) {
        return ResponseEntity.ok(animalService.updateAnimal(id, requestDTO));
    }

    @Operation(summary = "동물 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "동물 상태 수정")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestParam AnimalStatus status) {
        animalService.updateAnimalStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
