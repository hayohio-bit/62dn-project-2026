package com.dnproject.platform.service;

import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.Role;
import com.dnproject.platform.dto.request.ShelterSignupRequest;
import com.dnproject.platform.repository.ShelterRepository;
import com.dnproject.platform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShelterService {

    private final ShelterRepository shelterRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Long registerShelter(ShelterSignupRequest request, Long userId) {
        if (shelterRepository.existsByBusinessRegistrationNumber(request.getBusinessRegistrationNumber())) {
            throw new IllegalArgumentException("이미 등록된 사업자 번호입니다.");
        }
        if (shelterRepository.existsByEmail((request.getEmail()))) {
            throw new IllegalArgumentException("이미 사용 중인 이메일 입니다.");
        }

        User manager = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .role(Role.MANAGER)
                .build();

        userRepository.save(manager);

        Shelter shelter = request.toEntity(manager);

        return shelterRepository.save(shelter).getId();
    }

}
