package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FindEmailResponse {

    private String email;

    public static FindEmailResponse from(User user) {
        return FindEmailResponse.builder()
                .email(user.getEmail())
                .build();
    }

}
