package com.dnproject.platform.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRequest {
    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @NotNull(message = "동물 ID는 필수입니다.")
    private Long animalId;
}
