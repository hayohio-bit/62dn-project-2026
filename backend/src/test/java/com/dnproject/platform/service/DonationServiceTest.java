package com.dnproject.platform.service;

import com.dnproject.platform.domain.Donation;
import com.dnproject.platform.domain.DonationRequest;
import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.DonationStatus;
import com.dnproject.platform.domain.constant.DonationType;
import com.dnproject.platform.domain.constant.PaymentMethod;
import com.dnproject.platform.domain.constant.RequestStatus;
import com.dnproject.platform.dto.request.DonationApplyRequest;
import com.dnproject.platform.dto.response.DonationResponse;
import com.dnproject.platform.repository.DonationRepository;
import com.dnproject.platform.repository.DonationRequestRepository;
import com.dnproject.platform.repository.ShelterRepository;
import com.dnproject.platform.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
@Transactional
class DonationServiceTest {

    @Autowired
    private DonationService donationService;
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private DonationRequestRepository donationRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShelterRepository shelterRepository;

    private User savedUser;
    private Shelter savedShelter;

    @BeforeEach
    void setUp() {
        // 1. 공통 데이터 준비
        savedUser = userRepository.save(User.builder()
                .email("donor1@test.com")
                .name("기부천사1")
                .password("password1234!")
                .phone("010-1111-3333")
                .build());

        savedShelter = shelterRepository.save(Shelter.builder()
                .name("희망 보호소1")
                .address("서울시1")
                .phone("02-123-4568")
                .email("hope1@shelter.com")
                .email("shelter1@tes.com")
                .managerName("테스트관리자1")
                .managerPhone("010-5555-5555")
                .build());
    }

    @Test
    @DisplayName("물품 후원 신청시 수량 업데이트 테스트")
    // @Rollback(false) // 롤백을 하지 않고 커밋함
    void applyItemDonationTest() {
        // given: 기부 요청 공고 생성 (목표 100개)
        DonationRequest requestNotice = donationRequestRepository.save(DonationRequest.builder()
                .shelter(savedShelter)
                .title("강아지 사료가 필요해요")
                .content("급합니다!")
                .itemCategory("사료")
                .targetQuantity(100)
                .deadline(LocalDate.now().plusDays(7))
                .build());

        DonationApplyRequest applyRequest = DonationApplyRequest.builder()
                .userId(savedUser.getId())
                .shelterId(savedShelter.getId())
                .requestId(requestNotice.getId())
                .donorName("기부천사")
                .donorPhone("010-1111-2222")
                .donorEmail("donor@test.com")
                .donationType(DonationType.ITEM)
                .donationCategory("사료")
                .amount(new BigDecimal("50000"))
                .itemName("유기농 사료")
                .quantity(10) // 10개 기부
                .paymentMethod(PaymentMethod.PARCEL)
                .receiptRequested(false)
                .build();

        // when
        donationService.applyDonation(applyRequest);

        // then: 공고의 현재 수량이 0 -> 10으로 늘어났는지 검증
        DonationRequest updatedNotice = donationRequestRepository.findById(requestNotice.getId()).orElseThrow();

        assertEquals(10, updatedNotice.getCurrentQuantity());
        assertEquals(RequestStatus.OPEN, updatedNotice.getStatus());
    }

    @Test
    @DisplayName("기부 요청 수량을 모두 채우면 공고 상태가 CLOSED로 변경되어야 한다")
    void donationCompleteStatusTest() {
        // given: 목표 10개인 공고
        DonationRequest requestNotice = donationRequestRepository.save(DonationRequest.builder()
                .shelter(savedShelter)
                .title("소량 물품 요청")
                .itemCategory("간식")
                .targetQuantity(10)
                .deadline(LocalDate.now().plusDays(7))
                .content("테스트")
                .build());

        DonationApplyRequest applyRequest = DonationApplyRequest.builder()
                .userId(savedUser.getId())
                .shelterId(savedShelter.getId())
                .requestId(requestNotice.getId())
                .donorName("기부왕")
                .donorPhone("010-1111-2222")
                .donorEmail("king@test.com")
                .donationType(DonationType.ITEM)
                .donationCategory("사료")
                .amount(new BigDecimal("100000"))
                .quantity(10) // 목표 수량만큼 한 번에 기부
                .paymentMethod(PaymentMethod.DIRECT_DELIVERY)
                .receiptRequested(false)
                .build();

        // when
        donationService.applyDonation(applyRequest);

        // then
        DonationRequest updatedNotice = donationRequestRepository.findById(requestNotice.getId()).orElseThrow();
        assertEquals(10, updatedNotice.getCurrentQuantity());
        assertEquals(RequestStatus.CLOSED, updatedNotice.getStatus());
    }

    @Test
    @DisplayName("일반회원의 기부 내역 조회 테스트")
    void getMyDonationsTest() {
        // given
        // 먼저 기부 내역을 하나 생성함
        applyItemDonationTest();
        Long userId = savedUser.getId();

        // when
        List<DonationResponse> myDonations = donationService.getMyDonations(userId);

        // then
        assertFalse(myDonations.isEmpty(), "기부 내역이 DB에 없습니다.");

        myDonations.forEach(i -> log.info("기부 내역 리스트" + i));
    }

    @Test
    @DisplayName("보호소에 들어온 기부 목록 조회 테스트")
    void getShelterDonationsTest() {
        // given
        // 먼저 기부 내역을 하나 생성함
        applyItemDonationTest();
        Long shelterId = savedShelter.getId();

        // when
        List<DonationResponse> shelterDonations = donationService.getShelterDonations(shelterId);

        // then
        assertFalse(shelterDonations.isEmpty(), "내역이 없습니다.");

        shelterDonations.forEach(i -> log.info("기부 목록 내역 : " + i));
    }

    @Test
    @DisplayName("기부 상태 변경 테스트: PENDING -> RECEIVED")
    void updateDonationStatusTest() {
        // Given
        // 먼저 기부 내역을 하나 생성함
        applyItemDonationTest();
        Long userId = savedUser.getId();

        List<DonationResponse> myDonations = donationService.getMyDonations(userId);
        assertFalse(myDonations.isEmpty(), "테스트를 위한 기부 데이터가 DB에 있어야 합니다.");

        Long targetId = myDonations.get(0).getId(); // 첫 번째 기부 내역의 ID 선택
        DonationStatus newStatus = DonationStatus.RECEIVED; // 바꾸고 싶은 상태 설정

        // When
        donationService.updateDonationStatus(targetId, newStatus);

        // Then
        Donation updatedEntity = donationRepository.findById(targetId).orElseThrow();
        assertEquals(newStatus, updatedEntity.getStatus());

        log.info("상태 변경 완료 : ID " + targetId + " " + updatedEntity.getStatus() + "변경 완료");
    }
}