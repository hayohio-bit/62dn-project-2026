package com.dnproject.platform.service;

import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.dto.response.FavoriteResponse;
import com.dnproject.platform.repository.AnimalFavoriteRepository;
import com.dnproject.platform.repository.AnimalRepository;
import com.dnproject.platform.repository.ShelterRepository;
import com.dnproject.platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FavoriteServiceTest {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private AnimalFavoriteRepository animalFavoriteRepository;

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
                .email("fav@test.com")
                .name("찜유저")
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
                .species(com.dnproject.platform.domain.constant.Species.DOG)
                .build());
    }

    @Test
    @DisplayName("찜 토글 테스트 - 추가 및 삭제")
    void toggleFavoriteTest() {
        // 1. 추가
        FavoriteResponse addResponse = favoriteService.toggleFavorite(savedUser.getId(), savedAnimal.getId());
        assertThat(addResponse).isNotNull();
        assertThat(animalFavoriteRepository.existsByUserIdAndAnimalId(savedUser.getId(), savedAnimal.getId())).isTrue();

        // 2. 삭제
        FavoriteResponse removeResponse = favoriteService.toggleFavorite(savedUser.getId(), savedAnimal.getId());
        assertThat(removeResponse).isNull();
        assertThat(animalFavoriteRepository.existsByUserIdAndAnimalId(savedUser.getId(), savedAnimal.getId()))
                .isFalse();
    }

    @Test
    @DisplayName("내 찜 목록 조회 테스트")
    void getMyFavoritesTest() {
        // given
        favoriteService.toggleFavorite(savedUser.getId(), savedAnimal.getId());

        // when
        List<FavoriteResponse> responses = favoriteService.getMyFavorites(savedUser.getId());

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getAnimalName()).isEqualTo("바둑이");
    }
}
