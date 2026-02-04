package com.dnproject.platform.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preferences")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Preference extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String species;    // 선호 종 (개, 고양이 등)
    private Integer minAge;    // 희망 최소 나이
    private Integer maxAge;    // 희망 최대 나이
    private String size;       // 선호 크기 (소형, 중형, 대형)
    private String region;     // 선호 지역
    private String temperament; // 선호 성격

    @Builder
    public Preference(User user, String species, Integer minAge, Integer maxAge,
                      String size, String region, String temperament) {
        this.user = user;
        this.species = species;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.size = size;
        this.region = region;
        this.temperament = temperament;
    }

    // 선호도 정보 수정
    public void update(String species, Integer minAge, Integer maxAge,
                        String size, String region, String temperament) {
        this.species = species;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.size = size;
        this.region = region;
        this.temperament = temperament;
    }

}
