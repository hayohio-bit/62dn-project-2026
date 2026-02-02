package com.dnproject.platform.dto.request;

import com.dnproject.platform.domain.constant.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String email;
    private String password;
    private Role role;
}
