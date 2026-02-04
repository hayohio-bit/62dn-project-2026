package com.dnproject.platform.dto.response;


import com.dnproject.platform.domain.Preference;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PreferenceResponse {

    private Long id;
    private Long userId;
    private String species;
    private Integer minAge;
    private Integer maxAge;
    private String size;
    private String region;
    private String temperament;

    public static PreferenceResponse from(Preference preference) {
        return PreferenceResponse.builder()
                .id(preference.getId())
                .userId(preference.getUser().getId())
                .species(preference.getSpecies())
                .minAge(preference.getMinAge())
                .maxAge(preference.getMaxAge())
                .size(preference.getSize())
                .region(preference.getRegion())
                .temperament(preference.getTemperament())
                .build();
    }
}
