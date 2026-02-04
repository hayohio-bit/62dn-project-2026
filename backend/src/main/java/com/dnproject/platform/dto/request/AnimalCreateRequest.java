package com.dnproject.platform.dto.request;

import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.constant.Size;
import com.dnproject.platform.domain.constant.Species;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AnimalCreateRequest {
    private Long shelterId;
    private String publicApiAnimalId;
    private String orgName;
    private String chargeName;
    private String chargePhone;
    private Species species;
    private String breed;
    private String name;
    private Integer age;
    private String gender;
    private Size size;
    private BigDecimal weight;
    private String description;
    private String temperament;
    private String healthStatus;
    private Boolean neutered;
    private Boolean vaccinated;
    private String imageUrl;
    private LocalDate registerDate;

    // Mapping
    public Animal toEntity(Shelter shelter) {
        return Animal.builder()
                .shelter(shelter)
                .publicApiAnimalId(this.publicApiAnimalId)
                .orgName(this.orgName)
                .chargeName(this.chargeName)
                .chargePhone(this.chargePhone)
                .species(this.species)
                .breed(this.breed)
                .name(this.name)
                .age(this.age)
                .gender(this.gender)
                .size(this.size)
                .weight(this.weight)
                .description(this.description)
                .temperament(this.temperament)
                .healthStatus(this.healthStatus)
                .neutered(this.neutered != null ? this.neutered : false)
                .vaccinated(this.vaccinated != null ? this.vaccinated : false)
                .imageUrl(this.imageUrl)
                .status(Animal.AnimalStatus.PROTECTED) // 초기 상태값 설정
                .registerDate(this.registerDate)
                .build();
    }
}
