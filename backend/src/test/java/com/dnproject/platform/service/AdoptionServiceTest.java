package com.dnproject.platform.service;

import com.dnproject.platform.domain.Adoption;
import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.AdoptionStatus;
import com.dnproject.platform.domain.constant.AdoptionType;
import com.dnproject.platform.dto.request.AdoptionRequest;
import com.dnproject.platform.dto.response.AdoptionResponse;
import com.dnproject.platform.repository.AdoptionRepository;
import com.dnproject.platform.repository.AnimalRepository;
import com.dnproject.platform.repository.ShelterRepository;
import com.dnproject.platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class AdoptionServiceTest {

        @Autowired
        private AdoptionService adoptionService;

        @Autowired
        private AdoptionRepository adoptionRepository;

        @Autowired
        private AnimalRepository animalRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ShelterRepository shelterRepository;

        private User savedUser;
        private Animal savedAnimal;

        @BeforeEach
        void setUp() {
                savedUser = userRepository.save(User.builder()
                                .email("adopter@test.com")
                                .name("입양인")
                                .password("1234")
                                .build());

                Shelter shelter = shelterRepository.save(Shelter.builder()
                                .name("보호소")
                                .address("주소")
                                .phone("010-1234-5678")
                                .email("shelter@test.com")
                                .managerName("매니저")
                                .managerPhone("010-8888-9999")
                                .build());

                savedAnimal = animalRepository.save(Animal.builder()
                                .shelter(shelter)
                                .name("바둑이")
                                .build());
        }

        @Test
        @DisplayName("입양 신청 테스트")
        void applyAdoptionTest() {
                // given
                AdoptionRequest request = AdoptionRequest.builder()
                                .animalId(savedAnimal.getId())
                                .type(AdoptionType.ADOPTION)
                                .reason("강아지를 좋아해서")
                                .build();

                // when
                AdoptionResponse response = adoptionService.apply(request, savedUser.getId());

                // then
                assertThat(response.getAnimalName()).isEqualTo("바둑이");
                assertThat(response.getStatus()).isEqualTo(AdoptionStatus.PENDING.name());
                assertThat(adoptionRepository.existsById(response.getId())).isTrue();
        }

        @Test
        @DisplayName("입양 신청 취소 테스트")
        void cancelAdoptionTest() {
                // given
                Adoption adoption = adoptionRepository.save(Adoption.builder()
                                .user(savedUser)
                                .animal(savedAnimal)
                                .status(AdoptionStatus.PENDING)
                                .build());

                // when
                AdoptionResponse response = adoptionService.cancel(adoption.getId(), savedUser.getId());

                // then
                assertThat(response.getStatus()).isEqualTo(AdoptionStatus.CANCELLED.name());
        }

        @Test
        @DisplayName("입양 승인 테스트")
        void approveAdoptionTest() {
                // given
                Adoption adoption = adoptionRepository.save(Adoption.builder()
                                .user(savedUser)
                                .animal(savedAnimal)
                                .status(AdoptionStatus.PENDING)
                                .build());

                // when
                AdoptionResponse response = adoptionService.approve(adoption.getId());

                // then
                assertThat(response.getStatus()).isEqualTo(AdoptionStatus.APPROVED.name());
        }
}
