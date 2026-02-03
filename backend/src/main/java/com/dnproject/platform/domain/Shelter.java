package com.dnproject.platform.domain;

import com.dnproject.platform.domain.constant.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shelters")
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




}
