package com.dnproject.platform.dto.request;

import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShelterSignupRequest {

    @NotBlank(message = "보호소 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "보호소 주소는 필수입니다.")
    private String address;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @NotBlank(message = "보호소 연락처는 필수입니다.")
    private String phone;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "담당자 이름은 필수입니다.")
    private String managerName;

    @NotBlank(message = "담당자 연락처는 필수입니다.")
    private String managerPhone;

    @NotBlank(message = "사업자 등록번호는 필수입니다.")
    private String businessRegistrationNumber;

    private String businessRegistrationFile;

    private String publicApiShelterId;

    public Shelter toEntity(User manager){
        return Shelter.builder()
                .name(name)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .phone(phone)
                .email(email)
                .manager(manager)
                .managerName(managerName)
                .managerPhone(managerPhone)
                .businessRegistrationNumber(businessRegistrationNumber)
                .businessRegistrationFile(businessRegistrationFile)
                .publicApiShelterId(publicApiShelterId)
                .build();
    }
}
