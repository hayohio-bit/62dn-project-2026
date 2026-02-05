package com.antigravity.adoption.entity;

import com.antigravity.animal.entity.Animal;
import com.antigravity.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "adoption_requests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdoptionRequest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Column(nullable = false)
    private String applicantName;

    @Column(nullable = false)
    private String applicantContact;

    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdoptionStatus status;

    private LocalDateTime processedAt;

    public void approve() {
        this.status = AdoptionStatus.APPROVED;
        this.processedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = AdoptionStatus.REJECTED;
        this.processedAt = LocalDateTime.now();
    }

    public void cancel() {
        this.status = AdoptionStatus.CANCELED;
    }
}
