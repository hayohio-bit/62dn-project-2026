package com.antigravity.animal.dto;

import com.antigravity.animal.entity.Animal;
import com.antigravity.animal.entity.AnimalStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalRequestDTO {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "종(species)은 필수 입력 항목입니다.")
    private String species;

    private String breed;

    private Integer age;

    @NotBlank(message = "성별은 필수 입력 항목입니다.")
    private String gender;

    private Double weight;

    private Boolean neutered;

    private String healthStatus;

    @NotNull(message = "상태는 필수 입력 항목입니다.")
    private AnimalStatus status;

    private String imageUrl;

    public Animal toEntity() {
        return Animal.builder()
                .name(name)
                .species(species)
                .breed(breed)
                .age(age)
                .gender(gender)
                .weight(weight)
                .neutered(neutered)
                .healthStatus(healthStatus)
                .status(status)
                .imageUrl(imageUrl)
                .build();
    }
}
