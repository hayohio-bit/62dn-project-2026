package com.dnproject.platform.domain;

import com.dnproject.platform.domain.constant.DonationStatus;
import com.dnproject.platform.domain.constant.DonationType;
import com.dnproject.platform.domain.constant.DonorType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "donations", indexes = {
        @Index(name = "idx_donations_user", columnList = "user_id"),
        @Index(name = "idx_donations_shelter", columnList = "shelter_id")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Donation extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  //신청자 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id", nullable = false)
    private Shelter shelter;    //보호소 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private DonationRequest donationRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "donor_name", nullable = false, length = 50)
    private String donorName;

    @Column(name = "donor_birthdate")
    private LocalDate donorBirthdate;

    @Column(name = "donor_phone", nullable = false, length = 20)
    private String donorPhone;

    @Column(name = "donor_email", nullable = false, length = 100)
    private String donorEmail;

    @Column(name = "donor_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DonorType donorType = DonorType.INDIVIDUAL;

    @Column(name = "donation_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private DonationType donationType;

    @Column(name = "donation_category", nullable = false, length = 50)
    private String donationCategory;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;  //후원 금액

    @Column(name = "payment_method", nullable = false, length = 20)
    private String paymentMethod;   //결제 방법

    @Column(name = "receipt_requested")
    @Builder.Default
    private Boolean receiptRequested = false;

    @Column(name = "resident_registration_number", length = 20)
    private String residentRegistrationNumber;  //영수증용 주민번호

    @Column(name = "newsletter_consent")
    @Builder.Default
    private Boolean newsletterConsent = false;

    @Column(name = "item_name", length = 100)
    private String itemName;

    private Integer quantity;

    @Column(name = "delivery_method", length = 50)
    private String deliveryMethod;  // 배송 방법

    @Column(name = "tracking_number", length = 50)
    private String trackingNumber;  // 택배 송장 번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private DonationStatus status = DonationStatus.PENDING;

}
