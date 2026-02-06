package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.Preference;
import com.dnproject.platform.domain.constant.Size;
import com.dnproject.platform.domain.constant.Species;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PreferenceResponse {
    private Long id;
    private Species species;
    private Integer minAge;
    private Integer maxAge;
    private Size size;
    private String region;
    private String temperament;

    public static PreferenceResponse from(Preference preference) {
        return PreferenceResponse.builder()
                .id(preference.getId())
                .species(preference.getSpecies())
                .minAge(preference.getMinAge())
                .maxAge(preference.getMaxAge())
                .size(preference.getSize())
                .region(preference.getRegion())
                .temperament(preference.getTemperament())
                .build();
    }
}
