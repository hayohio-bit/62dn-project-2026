package com.dnproject.platform.service;

import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.Role;
import com.dnproject.platform.dto.response.AdminUserResponse;
import com.dnproject.platform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService {

    private final UserRepository userRepository;

    public Page<AdminUserResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(AdminUserResponse::from);
    }

    @Transactional
    public void updateUserRole(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        user.updateRole(role);
    }

    @Transactional
    public void suspendUser(Long userId, boolean suspend) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        user.setSuspended(suspend);
    }
}
