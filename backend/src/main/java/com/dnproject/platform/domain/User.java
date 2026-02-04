package com.dnproject.platform.domain;

/*
* id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 고유 번호 (PK)
    email VARCHAR(100) NOT NULL UNIQUE, -- 이메일 (로그인 ID 겸용)
    password VARCHAR(255) NOT NULL, -- 암호화된 비밀번호
    name VARCHAR(50) NOT NULL, -- 사용자 이름
    phone VARCHAR(20), -- 연락처
    address VARCHAR(255), -- 주소
    role VARCHAR(20) NOT NULL DEFAULT 'USER', -- 권한 (USER) MANAGER는 승인하면
    created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6), -- 등록일
    updated_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6), -- 수정일 (수정 시 자동 갱신)
* */

import com.dnproject.platform.domain.constant.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    private String phone;
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Volunteer> volunteers = new ArrayList<>();

    // 임시 비밀번호 여부를 위한 상태 값
    private boolean isTemporaryPassword = false;

    // 비즈니스 메소드 : 비밀번호 변경
    public void updatePassword(String newPassword){
        this.password = newPassword;
    }

}

