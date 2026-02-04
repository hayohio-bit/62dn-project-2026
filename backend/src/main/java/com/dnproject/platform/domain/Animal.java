package com.dnproject.platform.domain;

import com.dnproject.platform.domain.constant.AnimalStatus;
import com.dnproject.platform.domain.constant.Gender;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 유기동물 엔티티 - 공공 API 동기화 대상
 * 관계: Shelter(1), Adoptions(N), AnimalImages(N), AnimalFavorites(N)
 * auditing: BaseTimeEntity 상속으로 createdAt/updatedAt 자동 관리
 */
@Entity
@Table(name = "animals", indexes = {
        @Index(name = "idx_animal_species", columnList = "species"),
        @Index(name = "idx_animals_status", columnList = "status"),
        @Index(name = "idx_animals_shelter", columnList = "shelter_id"),
        @Index(name = "idx_animals_public_api_id", columnList = "public_api_animal_id")
})
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Animal extends BaseTimeEntity {  // auditing 상속

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // PK, 자동 증가

    @ManyToOne(fetch = FetchType.LAZY)  // 보호소 소속, 지연 로딩
    @JoinColumn(name = "shelter_id", nullable = false)
    private Shelter shelter;  // FK: shelter_id

    @Column(length = 50)
    private String publicApiAnimalId;  // 공공 API 고유 ID, 동기화 키

    @Column(length = 100)
    private String orgName;  // 보호기관명

    @Column(length = 50)
    private String chargeName;  // 담당자명

    @Column(length = 30)
    private String chargePhone;  // 담당자 연락처

    @Column(length = 10, nullable = false)
    private String species;  // 종류: DOG, CAT

    @Column(length = 50)
    private String breed;  // 품종

    @Column(length = 50)
    private String name;  // 동물명

    private Integer age;  // 나이 (개월)

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;  // 성별: M, F

    @Column(length = 10)
    private String size;  // 크기: S, M, L

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;  // 무게 (kg)

    @Column(columnDefinition = "TEXT")
    private String description;  // 상세 설명

    @Column(length = 100)
    private String temperament;  // 성격 (AI 매칭 키워드: 활발, 온순 등)

    @Column(length = 255)
    private String healthStatus;  // 건강상태

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean neutered = false;  // 중성화 여부

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean vaccinated = false;  // 예방접종 여부

    @Column(length = 500)
    private String imageUrl;  // 대표 이미지 URL (S3)

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    @Builder.Default
    private AnimalStatus status = AnimalStatus.PROTECTED;  // 상태: PROTECTED, ADOPTED 등

    private LocalDate registerDate;  // 접수일

    // 1:N 관계: 입양 신청
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Adoption> adoptions = new ArrayList<>();

    // 1:N 관계: 동물 이미지
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalImage> images = new ArrayList<>();

    // 1:N 관계: 즐겨찾기
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalFavorite> favorites = new ArrayList<>();

}
