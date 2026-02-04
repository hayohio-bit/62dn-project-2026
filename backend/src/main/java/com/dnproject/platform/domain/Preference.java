package com.dnproject.platform.domain;

import com.dnproject.platform.domain.constant.Size;
import com.dnproject.platform.domain.constant.Species;
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

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Species species;

    private Integer minAge;
    private Integer maxAge;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Size size;

    private String region;
    private String temperament;


    @Builder
    public Preference(User user, Species species, Integer minAge, Integer maxAge,
                      Size size, String region, String temperament) {
        this.user = user;
        this.species = species;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.size = size;
        this.region = region;
        this.temperament = temperament;
    }

    // 선호도 정보 수정
    public void update(Species species, Integer minAge, Integer maxAge,
                       Size size, String region, String temperament) {
        this.species = species;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.size = size;
        this.region = region;
        this.temperament = temperament;
    }

}
