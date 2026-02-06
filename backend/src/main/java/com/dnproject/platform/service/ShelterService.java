package com.dnproject.platform.service;

import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.Role;
import com.dnproject.platform.dto.request.ShelterSignupRequest;
import com.dnproject.platform.dto.request.ShelterVerifyRequest;
import com.dnproject.platform.dto.response.ShelterResponse;
import com.dnproject.platform.dto.response.ShelterSignupResponse;
import com.dnproject.platform.repository.ShelterRepository;
import com.dnproject.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShelterService {

    private final ShelterRepository shelterRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 보호소 회원 가입.
    @Transactional
    public ShelterSignupResponse registerShelter(ShelterSignupRequest request) {
        if (shelterRepository.existsByBusinessRegistrationNumber(request.getBusinessRegistrationNumber())) {
            throw new IllegalArgumentException("이미 등록된 사업자 번호입니다.");
        }
        if (userRepository.existsByEmail((request.getEmail()))) {
            throw new IllegalArgumentException("이미 사용 중인 이메일 입니다.");
        }

        User manager = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getManagerName())
                .phone(request.getManagerPhone())
                .role(Role.SHELTER_ADMIN)
                .build();

        userRepository.save(manager);

        Shelter shelter = request.toEntity(manager);
        Shelter savedShelter = shelterRepository.save(shelter);

        return ShelterSignupResponse.from(savedShelter);
    }

    public Page<ShelterResponse> getShelters(Pageable pageable) {
        return shelterRepository.findAll(pageable)
                .map(ShelterResponse::from);
    }

    // 보호소 상세 조회.
    public ShelterResponse getShelter(Long id) {
        Shelter shelter = shelterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 보호소를 찾을 수 없습니다."));

        return ShelterResponse.from(shelter);
    }

    // 보호소 상태 변경
    @Transactional
    public ShelterResponse verifyShelter(Long id, ShelterVerifyRequest request) {
        Shelter shelter = shelterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 보호소를 찾을 수 없습니다."));

        shelter.updateVerificationStatus(request.getStatus());

        return ShelterResponse.from(shelter);
    }

}
