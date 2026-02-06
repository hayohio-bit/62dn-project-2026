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
        if (userRepository.count() > 0)
            return;

        // 1. 관리자 계정 생성 (admin@test.com / admin123)
        User admin = User.builder()
                .email("admin@test.com")
                .password(passwordEncoder.encode("admin123"))
                .name("관리자")
                .phone("010-1234-5678")
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);

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

        // 3. 더미 동물 데이터 생성
        createAnimal(shelter, "초코", Species.DOG, "푸들", 2, "M", Size.SMALL, "온순하고 귀여워요.");
        createAnimal(shelter, "나비", Species.CAT, "코리안숏헤어", 1, "F", Size.SMALL, "애교가 많아요.");
        createAnimal(shelter, "백두", Species.DOG, "진돗개", 3, "M", Size.LARGE, "씩씩하고 건강합니다.");
        createAnimal(shelter, "미미", Species.CAT, "페르시안", 4, "F", Size.MEDIUM, "조용하고 차분합니다.");
    }

    private void createAnimal(Shelter shelter, String name, Species species, String breed, Integer age, String gender,
            Size size, String desc) {
        Animal animal = Animal.builder()
                .shelter(shelter)
                .name(name)
                .species(species)
                .breed(breed)
                .age(age)
                .gender(gender)
                .size(size)
                .weight(new BigDecimal("5.0"))
                .description(desc)
                .status(AnimalStatus.PROTECTED)
                .registerDate(LocalDate.now())
                .imageUrl("https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=500&auto=format") // 더미 이미지
                .build();
        animalRepository.save(animal);
    }
}
