package com.dnproject.platform.domain;

import com.dnproject.platform.domain.constant.AdoptionStatus;
import com.dnproject.platform.domain.constant.AdoptionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "adoptions", indexes = {
        @Index(columnList = "user_id"),
        @Index(columnList = "animal_id"),
        @Index(columnList = "status")
})
@Getter
@Setter
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class Adoption extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "animal_id")
    private Animal animal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AdoptionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private AdoptionStatus status = AdoptionStatus.PENDING;

    @Column(columnDefinition = "TEXT") private String reason;

    @Column(name = "processed_at") private LocalDateTime processedAt;
}
