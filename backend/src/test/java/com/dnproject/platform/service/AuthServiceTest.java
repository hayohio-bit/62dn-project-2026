package com.dnproject.platform.service;

import com.dnproject.platform.dto.request.LoginRequest;
import com.dnproject.platform.dto.request.SignupRequest;
import com.dnproject.platform.dto.response.TokenResponse;
import com.dnproject.platform.dto.response.UserResponse;
import com.dnproject.platform.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signupSuccess() {
        // given
        SignupRequest request = SignupRequest.builder()
                .email("newuser@test.com")
                .password("password123!")
                .passwordConfirm("password123!")
                .name("뉴유저")
                .phone("010-9999-8888")
                .build();

        // when
        UserResponse response = authService.signup(request);

        // then
        assertThat(response.getEmail()).isEqualTo("newuser@test.com");
        assertThat(userRepository.existsByEmail("newuser@test.com")).isTrue();
    }

    @Test
    @DisplayName("회원가입 실패 - 중복 이메일")
    void signupFailDuplicateEmail() {
        // given
        SignupRequest request = SignupRequest.builder()
                .email("duplicate@test.com")
                .password("password123!")
                .passwordConfirm("password123!")
                .name("복제인간")
                .build();
        authService.signup(request);

        // when & then
        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용 중인 이메일입니다.");
    }

    @Test
    @DisplayName("회원가입 실패 - 비밀번호 확인 불일치")
    void signupFailPasswordMismatch() {
        // given
        SignupRequest request = SignupRequest.builder()
                .email("mismatch@test.com")
                .password("password123!")
                .passwordConfirm("different!")
                .build();

        // when & then
        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() {
        // given
        SignupRequest signupRequest = SignupRequest.builder()
                .email("loginuser@test.com")
                .password("password123!")
                .passwordConfirm("password123!")
                .name("로그인유저")
                .build();
        authService.signup(signupRequest);

        LoginRequest loginRequest = LoginRequest.builder()
                .email("loginuser@test.com")
                .password("password123!")
                .build();

        // when
        TokenResponse response = authService.login(loginRequest);

        // then
        assertThat(response.getAccessToken()).isNotNull();
        assertThat(response.getEmail()).isEqualTo("loginuser@test.com");
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 비밀번호")
    void loginFailWrongPassword() {
        // given
        SignupRequest signupRequest = SignupRequest.builder()
                .email("wrongpwd@test.com")
                .password("password123!")
                .passwordConfirm("password123!")
                .name("비번틀림")
                .build();
        authService.signup(signupRequest);

        LoginRequest loginRequest = LoginRequest.builder()
                .email("wrongpwd@test.com")
                .password("wrong!")
                .build();

        // when & then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 또는 비밀번호가 올바르지 않습니다.");
    }
}
