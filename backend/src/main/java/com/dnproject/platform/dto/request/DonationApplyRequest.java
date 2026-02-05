package com.dnproject.platform.dto.request;

import com.dnproject.platform.domain.Donation;
import com.dnproject.platform.domain.DonationRequest;
import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.DonationType;
import com.dnproject.platform.domain.constant.PaymentMethod;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonationApplyRequest {
    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotNull(message = "보호소 ID는 필수입니다.")
    private Long shelterId;

    private Long requestId;

    @NotBlank(message = "후원자 성함은 필수입니다.")
    private String donorName;

    @NotBlank(message = "연락처는 필수입니다.")
    private String donorPhone;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String donorEmail;

    @NotBlank(message = "후원 종류는 필수입니다.")
    private DonationType donationType;

    @NotNull(message = "후원 금액(가치)은 필수입니다.")
    @DecimalMin(value = "0.0", inclusive = false, message = "후원 금액은 0보다 커야 합니다.")
    private BigDecimal amount;

    private String itemName;

    @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
    private Integer quantity;

    @NotBlank(message = "결제/전달 방법은 필수입니다.")
    private PaymentMethod paymentMethod;

    @NotNull(message = "영수증 신청 여부를 선택해주세요.")
    private Boolean receiptRequested;

    @NotBlank(message = "후원 카테고리는 필수입니다.")
    private String donationCategory;

    // DTO -> Entity 변환
    public Donation toEntity(User user, Shelter shelter, DonationRequest donationRequest) {
        return Donation.builder()
                .user(user)
                .shelter(shelter)
                .donationRequest(donationRequest)
                .donorName(donorName)
                .donorPhone(donorPhone)
                .donorEmail(donorEmail)
                .donationType(donationType)
                .donationCategory(donationCategory)
                .amount(amount)
                .itemName(itemName)
                .quantity(quantity)
                .paymentMethod(paymentMethod)
                .receiptRequested(receiptRequested)
                .build();
    }
}