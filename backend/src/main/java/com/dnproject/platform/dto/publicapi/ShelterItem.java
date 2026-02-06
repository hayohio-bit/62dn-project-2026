package com.dnproject.platform.dto.publicapi;

import lombok.Data;

@Data
public class ShelterItem {
    private String careRegNo; // 보호소번호 (DB: public_api_shelter_id)
    private String careNm; // 보호소명
}
