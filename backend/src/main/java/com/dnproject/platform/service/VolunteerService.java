package com.dnproject.platform.service;

import com.dnproject.platform.domain.*;
import com.dnproject.platform.domain.constant.VolunteerStatus;
import com.dnproject.platform.dto.request.VolunteerApplyRequest;
import com.dnproject.platform.dto.request.VolunteerRecruitmentCreateRequest;
import com.dnproject.platform.dto.response.VolunteerRecruitmentResponse;
import com.dnproject.platform.dto.response.VolunteerResponse;
import com.dnproject.platform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final VolunteerRecruitmentRepository recruitmentRepository;
    private final ShelterRepository shelterRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 봉사활동 모집 공고 만들기
    public VolunteerRecruitmentResponse createRecruitment(VolunteerRecruitmentCreateRequest request) {

        // 1. 보호소 존재 여부 체크 및 예외 처리
        Shelter shelter = shelterRepository.findById(request.getShelterId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보호소 입니다."));

        // 2. 보호소의 정보를 모집 봉사모집 Entity에 추가
        VolunteerRecruitment recruitment = request.toEntity(shelter);

        // 3. DB에 저장
        VolunteerRecruitment saved = recruitmentRepository.save(recruitment);

        // 4. Entity에서 Dto로 변환하여 저장된 정보를 꺼내어 리턴
        return VolunteerRecruitmentResponse.from(saved);
    }

    // 봉사활동 신청하기
    public VolunteerResponse applyVolunteer(Long userId, VolunteerApplyRequest request) {

        // 1. 회원과 보호소 존재 여부 체크 및 예외 처리
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Shelter shelter = shelterRepository.findById(request.getShelterId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보호소입니다."));

        // 2. 모집 공고나 게시글이 없으면 처리 X 있을때만 처리
        VolunteerRecruitment recruitment = null;
        if (request.getRecruitmentId() != null) {
            recruitment = recruitmentRepository.findById(request.getRecruitmentId()).orElse(null);
        }

        Board board = null;
        if (request.getBoardId() != null) {
            board = boardRepository.findById(request.getBoardId()).orElse(null);
        }

        // 3. 유저 정보, 보호소 정보, 공고 정보, 게시글 정보를 Entity로 변환 후 저장
        Volunteer volunteer = request.toEntity(user, shelter, recruitment, board);
        Volunteer saved = volunteerRepository.save(volunteer);

        // 4. DB에 저장되어있는 Entity를 Dto로 변환 후 리턴
        return VolunteerResponse.from(saved);
    }

    // 보호소별 봉사 신청 내역 조회
    public Page<VolunteerResponse> getVolunteerApplicationsByShelter(Long shelterId, Pageable pageable) {
        return volunteerRepository.findByShelterId(shelterId, pageable)
                .map(VolunteerResponse::from);
    }

    // 봉사 신청 상태 변경
    public VolunteerResponse updateApplicationStatus(Long volunteerId, VolunteerStatus status) {
        // 1. 봉사 내역의 존재 여부와 상세 정보
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신청 내역이 없습니다."));

        // 2. 신청 내역의 상태를 변경
        volunteer.updateStatus(status);
        
        // 3. 변경된 상태의 내역을 DB에 저장
        Volunteer updatedVolunteer = volunteerRepository.save(volunteer);
        
        // 4. DB에 저장되어있는 내역을 Dto로 변경해서 리턴
        return VolunteerResponse.from(updatedVolunteer);
    }

    // 모집 공고 전체 조회
    @Transactional(readOnly = true)
    public Page<VolunteerRecruitmentResponse> getRecruitments(Pageable pageable) {
        return recruitmentRepository.findAll(pageable)
                .map(VolunteerRecruitmentResponse::from);
    }

    // 보호소 별 모집 공고 조회
    @Transactional(readOnly = true)
    public Page<VolunteerRecruitmentResponse> getRecruitmentsByShelter(Long shelterId, Pageable pageable) {
        return recruitmentRepository.findByShelterId(shelterId, pageable)
                .map(VolunteerRecruitmentResponse::from);
    }
}
