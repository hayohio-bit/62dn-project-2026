package com.dnproject.platform.service;

import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.Size;
import com.dnproject.platform.domain.constant.Species;
import com.dnproject.platform.dto.request.PreferenceRequest;
import com.dnproject.platform.dto.response.PreferenceResponse;
import com.dnproject.platform.repository.PreferenceRepository;
import com.dnproject.platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PreferenceServiceTest {

    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PreferenceRepository preferenceRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        savedUser = userRepository.save(User.builder()
                .email("pref@test.com")
                .name("선호유저")
                .password("1234")
                .build());
    }

    @Test
    @DisplayName("선호도 저장 및 조회 테스트")
    void saveAndGetPreferenceTest() {
        // given
        PreferenceRequest request = new PreferenceRequest();
        request.setSpecies(Species.DOG);
        request.setMinAge(1);
        request.setMaxAge(5);
        request.setSize(Size.MEDIUM);
        request.setRegion("서울");

        // when
        PreferenceResponse saved = preferenceService.savePreference(savedUser.getId(), request);
        PreferenceResponse fetched = preferenceService.getPreference(savedUser.getId());

        // then
        assertThat(saved.getSpecies()).isEqualTo(Species.DOG);
        assertThat(fetched).isNotNull();
        assertThat(fetched.getSpecies()).isEqualTo(Species.DOG);
        assertThat(fetched.getRegion()).isEqualTo("서울");
    }
}
