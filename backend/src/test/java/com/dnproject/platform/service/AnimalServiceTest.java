package com.dnproject.platform.service;

import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.constant.AnimalStatus;
import com.dnproject.platform.domain.constant.Gender;
import com.dnproject.platform.domain.constant.Species;
import com.dnproject.platform.dto.request.AnimalCreateRequest;
import com.dnproject.platform.dto.response.AnimalResponse;
import com.dnproject.platform.repository.AnimalRepository;
import com.dnproject.platform.repository.ShelterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AnimalServiceTest {

        @Autowired
        private AnimalService animalService;

        @Autowired
        private ShelterRepository shelterRepository;

        @Autowired
        private AnimalRepository animalRepository;

        private Shelter savedShelter;

        @BeforeEach
        void setUp() {
                savedShelter = shelterRepository.save(Shelter.builder()
                                .name("애니멀홈")
                                .address("서울")
                                .phone("02-111-2222")
                                .email("home@animal.com")
                                .managerName("김관리")
                                .managerPhone("010-1111-2222")
                                .build());
        }

        @Test
        @DisplayName("동물 등록 테스트")
        void createAnimalTest() {
                // given
                AnimalCreateRequest request = AnimalCreateRequest.builder()
                                .shelterId(savedShelter.getId())
                                .name("뽀삐")
                                .species(Species.DOG)
                                .breed("말티즈")
                                .age(2)
                                .gender("M")
                                .build();

                // when
                AnimalResponse response = animalService.createAnimal(request);

                // then
                assertThat(response.getName()).isEqualTo("뽀삐");
                assertThat(response.getSpecies()).isEqualTo(Species.DOG);
                assertThat(animalRepository.existsById(response.getId())).isTrue();
        }

        @Test
        @DisplayName("동물 조회 테스트")
        void getAnimalTest() {
                // given
                Animal animal = animalRepository.save(Animal.builder()
                                .shelter(savedShelter)
                                .name("해피")
                                .species(Species.DOG)
                                .status(AnimalStatus.PROTECTED)
                                .build());

                // when
                AnimalResponse response = animalService.getAnimal(animal.getId());

                // then
                assertThat(response.getName()).isEqualTo("해피");
        }

        @Test
        @DisplayName("동물 목록 필터링 조회 테스트")
        void getAnimalsFilteringTest() {
                // given
                animalRepository.save(Animal.builder()
                                .shelter(savedShelter)
                                .name("멍멍이")
                                .species(Species.DOG)
                                .status(AnimalStatus.PROTECTED)
                                .build());
                animalRepository.save(Animal.builder()
                                .shelter(savedShelter)
                                .name("야옹이")
                                .species(Species.CAT)
                                .status(AnimalStatus.PROTECTED)
                                .build());

                // when
                Page<AnimalResponse> dogs = animalService.getAnimals(AnimalStatus.PROTECTED, "DOG", null, null, null,
                                null,
                                PageRequest.of(0, 10));
                Page<AnimalResponse> cats = animalService.getAnimals(AnimalStatus.PROTECTED, "CAT", null, null, null,
                                null,
                                PageRequest.of(0, 10));

                // then
                assertThat(dogs.getContent()).hasSize(1);
                assertThat(dogs.getContent().get(0).getName()).isEqualTo("멍멍이");
                assertThat(cats.getContent()).hasSize(1);
                assertThat(cats.getContent().get(0).getName()).isEqualTo("야옹이");
        }
}
