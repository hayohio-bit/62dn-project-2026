package com.dnproject.platform.service;

import com.dnproject.platform.domain.Preference;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.dto.request.PreferenceRequest;
import com.dnproject.platform.dto.response.PreferenceResponse;
import com.dnproject.platform.repository.PreferenceRepository;
import com.dnproject.platform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final UserRepository userRepository;

    public PreferenceResponse getPreference(Long userId) {
        return preferenceRepository.findByUserId(userId)
                .map(PreferenceResponse::from)
                .orElse(null);
    }

    @Transactional
    public PreferenceResponse savePreference(Long userId, PreferenceRequest request) {
        Preference preference = preferenceRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
                    return Preference.builder().user(user).build();
                });

        preference.update(
                request.getSpecies(),
                request.getMinAge(),
                request.getMaxAge(),
                request.getSize(),
                request.getRegion(),
                request.getTemperament());

        Preference saved = preferenceRepository.save(preference);
        return PreferenceResponse.from(saved);
    }
}
