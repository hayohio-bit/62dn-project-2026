package com.dnproject.platform.dto.request;

import com.dnproject.platform.domain.constant.Size;
import com.dnproject.platform.domain.constant.Species;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceRequest {
    private Species species;
    private Integer minAge;
    private Integer maxAge;
    private Size size;
    private String region;
    private String temperament;
}
