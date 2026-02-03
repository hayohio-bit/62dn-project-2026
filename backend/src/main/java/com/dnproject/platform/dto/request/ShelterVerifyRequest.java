package com.dnproject.platform.dto.request;

import com.dnproject.platform.domain.constant.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShelterVerifyRequest {
    private VerificationStatus status;
}
