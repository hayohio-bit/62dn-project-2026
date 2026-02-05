package com.dnproject.platform.service;

import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.VolunteerRecruitment;
import com.dnproject.platform.domain.constant.ActivityCycle;
import com.dnproject.platform.domain.constant.VolunteerType;
import com.dnproject.platform.dto.request.VolunteerApplyRequest;
import com.dnproject.platform.dto.request.VolunteerRecruitmentCreateRequest;
import com.dnproject.platform.dto.response.VolunteerRecruitmentResponse;
import com.dnproject.platform.dto.response.VolunteerResponse;
import com.dnproject.platform.repository.ShelterRepository;
import com.dnproject.platform.repository.UserRepository;
import com.dnproject.platform.repository.VolunteerRecruitmentRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
@Transactional
class VolunteerServiceTest {

    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private ShelterRepository shelterRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VolunteerRecruitmentRepository recruitmentRepository;

    private Shelter savedShelter;
    private User savedUser;

    @BeforeEach
    void setUp() {
        savedShelter = shelterRepository.save(Shelter.builder()
                .name("행복 보호소")
                .address("서울")
                .phone("02-123-4567")
                .email("shelter@tes.com")
                .managerName("테스트관리자")
                .managerPhone("010-4444-5555")
                .build());

        savedUser = userRepository.save(User.builder()
                .email("test@test.com")
                .name("홍길동")
                .password("1234")
                .build());
    }

    @Test
    @DisplayName("봉사 모집 공고 생성 테스트")
    void createRecruitmentTest() {
        // given
        VolunteerRecruitmentCreateRequest request = VolunteerRecruitmentCreateRequest.builder()
                .shelterId(savedShelter.getId())
                .title("주말 강아지 산책 봉사 모집")
                .content("강아지들과 함께 산책하실 분!")
                .maxApplicants(5)
                .deadline(LocalDate.now().plusDays(7))
                .build();

        // when
        VolunteerRecruitmentResponse response = volunteerService.createRecruitment(request);

        // then
        assertThat(response.getTitle()).isEqualTo("주말 강아지 산책 봉사 모집");
        assertThat(response.getShelterName()).isEqualTo("행복 보호소");
    }

    @Test
    @DisplayName("봉사활동 신청 테스트")
    void applyVolunteerTest() {
        // given
        VolunteerRecruitment recruitment = recruitmentRepository.save(VolunteerRecruitment.builder()
                .shelter(savedShelter)
                .title("공고")
                .content("내용")
                .maxApplicants(10)
                .deadline(LocalDate.now().plusDays(7))
                .build());

        VolunteerApplyRequest request = VolunteerApplyRequest.builder()
                .shelterId(savedShelter.getId())
                .recruitmentId(recruitment.getId())
                .applicantName("신청자")
                .applicantPhone("010-1111-2222")
                .applicantEmail("apply@test.com")
                .activityRegion("서울")
                .activityField("산책")
                .activityCycle(ActivityCycle.ONCE)
                .volunteerDateStart(LocalDate.now())
                .volunteerType(VolunteerType.INDIVIDUAL)
                .build();

        // when
        VolunteerResponse response = volunteerService.applyVolunteer(savedUser.getId(), request);

        // then
        assertThat(response.getApplicantName()).isEqualTo("신청자");
        assertThat(response.getVolunteerStatus()).isEqualTo("승인 대기"); // PENDING의 설명값

    }
}