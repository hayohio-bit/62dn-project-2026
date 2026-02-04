package com.dnproject.platform.dto.request;

import com.dnproject.platform.domain.Adoption;
import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.AdoptionStatus;
import com.dnproject.platform.domain.constant.AdoptionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionRequest {

    @NotNull(message = "신청할 동물 ID는 필수입니다.")
    private Long animalId;

    @NotNull(message = "입양/임보 유형을 선택해주세요.")
    private AdoptionType type;

    @NotNull(message = "입양 신청 사유를 작성해주세요.")
    private String reason;

    // dto -> entity 변환 메서드
    public Adoption toEntity(User user, Animal animal) {
        return Adoption.builder()
                .user(user)
                .animal(animal)
                .type(type)
                .reason(reason)
                .status(AdoptionStatus.PENDING)
                .build();
    }
}
