package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.constant.Size;
import com.dnproject.platform.domain.constant.Species;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class AnimalListResponse {
    private Long id;
    private String name;
    private Species species;
    private String breed;
    private String gender;
    private Size size;
    private String status;
    private String imageUrl;
    private String orgName;   // 어느 지역/기관인지?
    private LocalDate registerDate;

    //Mapping
    public static AnimalListResponse from(Animal animal) {
        return AnimalListResponse.builder()
                .id(animal.getId())
                .name(animal.getName())
                .species(animal.getSpecies())
                .breed(animal.getBreed())
                .gender(animal.getGender())
                .size(animal.getSize())
                .status(animal.getStatus().name())
                .imageUrl(animal.getImageUrl())
                .orgName(animal.getOrgName())
                .registerDate(animal.getRegisterDate())
                .build();
    }
}
