package com.dnproject.platform.service;

import com.dnproject.platform.domain.constant.VerificationStatus;
import com.dnproject.platform.dto.request.ShelterSignupRequest;
import com.dnproject.platform.dto.request.ShelterVerifyRequest;
import com.dnproject.platform.dto.response.ShelterResponse;
import com.dnproject.platform.dto.response.ShelterSignupResponse;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Log4j2
@Transactional
class ShelterServiceTest {

    @Autowired
    private ShelterService shelterService;

    @Test
    @DisplayName("보호소 회원가입 테스트")
    public void shelterSignTest() {
        // given
        ShelterSignupRequest request = ShelterSignupRequest.builder()
                .email("test@test.com")
                .password("1234")
                .name("test 보호소")
                .phone("02-1234-1234")
                .businessRegistrationNumber("111-11-11111")
                .managerName("홍길동")
                .managerPhone("010-1234-5678")
                .address("강동구 천호동")
                .build();

        // when
        ShelterSignupResponse response = shelterService.registerShelter(request);

        // then
        log.info("생성된 id : " + response.getShelterId());

        assertThat(response.getShelterId()).isGreaterThan(0L); // Id가 0보다 높은지 검증
        assertThat(response.getShelterName()).isEqualTo("test 보호소");

    }

    @Test
    @DisplayName("보호소 승인 상태 변경 테스트")
    public void verifyShelterTest() {
        // given
        // 1. 회원가입 신청
        ShelterSignupRequest signupRequest = ShelterSignupRequest.builder()
                .email("test@test.com")
                .password("1234")
                .name("test 보호소")
                .phone("02-1234-1234")
                .businessRegistrationNumber("111-11-11111")
                .managerName("홍길동")
                .managerPhone("010-1234-5678")
                .address("강동구 천호동")
                .build();

        // 2. 가입처리 등록
        ShelterSignupResponse signupResponse = shelterService.registerShelter(signupRequest);
        Long shelterId = signupResponse.getShelterId();

        // 3. 상태변경 (인증완료 상태로 변경)
        ShelterVerifyRequest verifyRequest = new ShelterVerifyRequest(VerificationStatus.VERIFIED);

        // when
        // 승인
        ShelterResponse result = shelterService.verifyShelter(shelterId, verifyRequest);

        // then
        assertThat(result.getStatus()).isEqualTo(VerificationStatus.VERIFIED.getDescription()); // 잘 바뀌었나 확인.
        assertThat(result.getVerifiedAt()).isNotNull(); // 승인 시간이 제대로 찍혔는지 확인.

        log.info("승인 상태 확인 : " + result.getStatus());
        log.info("승인 시간 확인 : " + result.getVerifiedAt());

    }

}