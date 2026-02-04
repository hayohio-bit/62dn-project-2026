package com.dnproject.platform.dto.request;


import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PreferenceRequest {
    private String species;
    private Integer minAge;
    private Integer maxAge;
    private String size;
    private String region;
    private String temperament;
}
