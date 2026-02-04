package com.dnproject.platform.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "animal_favorite",
        uniqueConstraints = {
                // 동일한 유저가 중복해서 찜하는 것을 막는다
                @UniqueConstraint(
                        name = "uk_favorites_user_animal",
                        columnNames = {"user_id", "animal_id"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Favorite extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Builder
    public Favorite(User user, Animal animal) {
        this.user = user;
        this.animal = animal;
    }
}
