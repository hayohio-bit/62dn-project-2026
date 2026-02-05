package com.dnproject.platform.domain;

import com.dnproject.platform.domain.constant.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shelters", indexes = {
        @Index(name = "idx_shelters_name", columnList = "name"),
        @Index(name = "idx_shelters_business_reg", columnList = "business_registration_number"),
        @Index(name = "idx_shelters_verification", columnList = "verification_status")
})
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Shelter extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;    // 위도

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;   // 경도

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 100)
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    @Column(name = "manager_name", nullable = false, length = 50)
    private String managerName;

    @Column(name = "manager_phone", nullable = false, length = 20)
    private String managerPhone;

    @Column(name = "business_registration_number", unique = true, length = 20)
    private String businessRegistrationNumber;

    @Column(name = "business_registration_file", length = 500)
    private String businessRegistrationFile;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 20)    // ENUM
    @Builder.Default
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @Column(name = "public_api_shelter_id", length = 50)
    private String publicApiShelterId;  // 공공 데이터 api 연동용

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @OneToMany(mappedBy = "shelter")
    @Builder.Default
    private List<Volunteer> volunteers = new ArrayList<>();

    @OneToMany(mappedBy = "shelter")
    private List<VolunteerRecruitment> volunteerRecruitments = new ArrayList<>();

    public void updateVerificationStatus(VerificationStatus status) {
        this.verificationStatus = status;

        if (status == VerificationStatus.VERIFIED) {
            this.verifiedAt = LocalDateTime.now();
        }
    }


}
