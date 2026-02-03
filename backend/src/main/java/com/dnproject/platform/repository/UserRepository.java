package com.dnproject.platform.repository;


import com.dnproject.platform.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 시큐리리 인증 시 이메일로 사용자 찾는다
    Optional<User> findByEmail(String email);

    // 이메일 중복 췍
    boolean existsByEmail(String email);

}
