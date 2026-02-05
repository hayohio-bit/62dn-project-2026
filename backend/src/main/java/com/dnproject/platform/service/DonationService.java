package com.dnproject.platform.service;

import com.dnproject.platform.domain.Donation;
import com.dnproject.platform.domain.DonationRequest;
import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.DonationStatus;
import com.dnproject.platform.domain.constant.DonationType;
import com.dnproject.platform.dto.request.DonationApplyRequest;
import com.dnproject.platform.dto.request.DonationRequestCreateRequest;
import com.dnproject.platform.dto.response.DonationRequestResponse;
import com.dnproject.platform.dto.response.DonationResponse;
import com.dnproject.platform.repository.DonationRepository;
import com.dnproject.platform.repository.DonationRequestRepository;
import com.dnproject.platform.repository.ShelterRepository;
import com.dnproject.platform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DonationService {

    private final DonationRepository donationRepository;
    private final DonationRequestRepository donationRequestRepository;
    private final UserRepository userRepository;
    private final ShelterRepository shelterRepository;

    // 회원들이 기부하겠다고 신청하는 로직
    @Transactional
    public DonationResponse applyDonation(DonationApplyRequest request) {
        // 1. user, shelter entity 조회
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        Shelter shelter = shelterRepository.findById(request.getShelterId())
                .orElseThrow(() -> new EntityNotFoundException("보호소를 찾을 수 없습니다."));

        DonationRequest donationRequest = null;

        // 기부 요청 공고가 있다면 진입
        if (request.getRequestId() != null) {
            donationRequest = donationRequestRepository.findById(request.getRequestId())
                    .orElseThrow(() -> new EntityNotFoundException("기부 요청 정보를 찾을 수 없습니다."));

            // 물품 기부인 경우엔 수량 업데이트
            if (request.getDonationType() == DonationType.ITEM && request.getQuantity() != null) {
                donationRequest.updateCurrentQuantity(request.getQuantity());
            }
        }

        // Donation 엔티티로 변환 및 저장.
        Donation donation = request.toEntity(user, shelter, donationRequest);
        Donation saved = donationRepository.save(donation);

        // DB에 저장되어있는 데이터 Dto로 변환해서 리턴
        return DonationResponse.from(saved);
    }

    // 보호소에서 기부 요청 공고를 올리는 로직
    @Transactional
    public DonationRequestResponse createDonationRequest(DonationRequestCreateRequest request) {

        // 보호소 존재 여부 체크
        Shelter shelter = shelterRepository.findById(request.getShelterId())
                .orElseThrow(() -> new EntityNotFoundException("보호소를 찾을 수 없습니다."));

        // Dto를 Entity로 변환 후 저장
        DonationRequest donationRequest = request.toEntity(shelter);
        DonationRequest saved = donationRequestRepository.save(donationRequest);

        // DB에서 데이터 꺼내서 Dto로 변환 후 반환
        return DonationRequestResponse.from(saved);
    }

    // 사용자 기부 내역 조회
    public List<DonationResponse> getMyDonations(Long userId) {
        return donationRepository.findAllByUserId(userId).stream()
                .map(DonationResponse::from)
                .toList();
    }

    // 보호소에 들어온 기부 목록 조회
    public List<DonationResponse> getShelterDonations(Long shelterId) {
        return donationRepository.findAllByShelterId(shelterId).stream()
                .map(DonationResponse::from)
                .toList();
    }

    // 기부 상태 변경
    @Transactional
    public DonationResponse updateDonationStatus(Long donationId, DonationStatus status) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new EntityNotFoundException("해당 기부 내역을 찾을 수 없습니다."));

        donation.updateStatus(status);

        return DonationResponse.from(donation);
    }
}
