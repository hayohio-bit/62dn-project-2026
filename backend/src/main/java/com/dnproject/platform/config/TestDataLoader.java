package com.dnproject.platform.config;

import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.AnimalStatus;
import com.dnproject.platform.domain.constant.Role;
import com.dnproject.platform.domain.constant.Size;
import com.dnproject.platform.domain.constant.Species;
import com.dnproject.platform.domain.constant.VerificationStatus;
import com.dnproject.platform.repository.AnimalRepository;
import com.dnproject.platform.repository.ShelterRepository;
import com.dnproject.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TestDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ShelterRepository shelterRepository;
    private final AnimalRepository animalRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("DEBUG: TestDataLoader started");
        if (userRepository.count() > 0) {
            System.out.println("DEBUG: Users already exist, skipping TestDataLoader");
            return;
        }

        // 1. 관리자 계정 생성 (admin@test.com / admin123)
        User admin = User.builder()
                .email("admin@test.com")
                .password(passwordEncoder.encode("admin123"))
                .name("관리자")
                .phone("010-1234-5678")
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);
        System.out.println("DEBUG: Admin user created");

        // 2. 테스트 보호소 생성
        Shelter shelter = Shelter.builder()
                .name("행복 동물 보호소")
                .address("서울시 강남구")
                .phone("02-123-4567")
                .email("shelter@test.com") // 필수 필드 추가
                .managerName("보호소장")
                .managerPhone("010-9999-8888")
                .verificationStatus(VerificationStatus.VERIFIED)
                .build();
        shelterRepository.save(shelter);
        System.out.println("DEBUG: Test shelter created");
        System.out.println("DEBUG: TestDataLoader finished");

    }

}
