package com.dnproject.platform.domain;

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

    @Enumerated private AdoptionType type = AdoptionType.ADOPTION;

    @Enumerated private AdoptionStatus status = AdoptionStatus.PENDING;

    @Column(columnDefinition = "TEXT") private String reason;

    @Column(name = "processed_at") private LocalDateTime processedAt;

    public enum AdoptionType { ADOPTION, FOSTER }
    public enum AdoptionStatus { PENDING, APPROVED, REJECTED }
}
