package com.dnproject.platform.controller;

import com.dnproject.platform.domain.constant.AnimalStatus;
import com.dnproject.platform.dto.request.AnimalCreateRequest;
import com.dnproject.platform.dto.response.AnimalResponse;
import com.dnproject.platform.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAnimals(
            @RequestParam(required = false) AnimalStatus status,
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String breed,
            Pageable pageable) {
        Page<AnimalResponse> animals = animalService.getAnimals(status, species, breed, pageable);
        return ResponseEntity.ok(Map.of(
                "data", Map.of(
                        "content", animals.getContent(),
                        "totalPages", animals.getTotalPages(),
                        "totalElements", animals.getTotalElements())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAnimal(@PathVariable Long id) {
        AnimalResponse animal = animalService.getAnimal(id);
        return ResponseEntity.ok(Map.of("data", animal));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAnimal(@RequestBody AnimalCreateRequest request) {
        AnimalResponse animal = animalService.createAnimal(request);
        return ResponseEntity.ok(Map.of("data", animal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAnimal(@PathVariable Long id,
            @RequestBody AnimalCreateRequest request) {
        AnimalResponse animal = animalService.updateAnimal(id, request);
        return ResponseEntity.ok(Map.of("data", animal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/shelter/{shelterId}")
    public ResponseEntity<Map<String, Object>> getAnimalsByShelter(@PathVariable Long shelterId, Pageable pageable) {
        Page<AnimalResponse> animals = animalService.getAnimalsByShelter(shelterId, pageable);
        return ResponseEntity.ok(Map.of(
                "data", Map.of(
                        "content", animals.getContent(),
                        "totalPages", animals.getTotalPages(),
                        "totalElements", animals.getTotalElements())));
    }
}
