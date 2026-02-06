package com.dnproject.platform.dto.request;

import com.dnproject.platform.domain.constant.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRoleUpdateRequest {
    private Role role;
}
