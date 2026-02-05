package com.antigravity.animal.dto;

import com.antigravity.animal.entity.Animal;
import com.antigravity.animal.entity.AnimalStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalResponseDTO {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private Integer age;
    private String gender;
    private Double weight;
    private Boolean neutered;
    private String healthStatus;
    private AnimalStatus status;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AnimalResponseDTO fromEntity(Animal animal) {
        return AnimalResponseDTO.builder()
                .id(animal.getId())
                .name(animal.getName())
                .species(animal.getSpecies())
                .breed(animal.getBreed())
                .age(animal.getAge())
                .gender(animal.getGender())
                .weight(animal.getWeight())
                .neutered(animal.getNeutered())
                .healthStatus(animal.getHealthStatus())
                .status(animal.getStatus())
                .imageUrl(animal.getImageUrl())
                .createdAt(animal.getCreatedAt())
                .updatedAt(animal.getUpdatedAt())
                .build();
    }
}
