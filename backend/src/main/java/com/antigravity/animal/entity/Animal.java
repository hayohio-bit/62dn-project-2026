package com.antigravity.animal.entity;

import com.antigravity.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "animals")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Animal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String species; // 종 (Dog, Cat 등)

    private String breed; // 품종

    private Integer age;

    @Column(nullable = false)
    private String gender;

    private Double weight;

    private Boolean neutered; // 중성화 여부

    private String healthStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnimalStatus status;

    private String imageUrl; // 대표 이미지 경로

    // 비즈니스 로직: 정보 수정
    public void update(String name, String species, String breed, Integer age,
            String gender, Double weight, Boolean neutered,
            String healthStatus, AnimalStatus status, String imageUrl) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.neutered = neutered;
        this.healthStatus = healthStatus;
        this.status = status;
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
    }

    public void updateStatus(AnimalStatus status) {
        this.status = status;
    }

    public boolean isAdoptable() {
        return this.status == AnimalStatus.AVAILABLE;
    }
}
