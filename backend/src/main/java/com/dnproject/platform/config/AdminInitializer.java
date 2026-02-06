package com.dnproject.platform.config;

import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.Role;
import com.dnproject.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminEmail = "admin@admin.com";
        java.util.Optional<User> userOpt = userRepository.findByEmail(adminEmail);

        if (userOpt.isEmpty()) {
            log.info("Creating default admin account: {}", adminEmail);
            User admin = User.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode("1234"))
                    .name("시스템관리자")
                    .role(Role.SUPER_ADMIN)
                    .build();
            userRepository.save(admin);
            log.info("Admin account initialized successfully as SUPER_ADMIN.");
        } else {
            User existingUser = userOpt.get();
            if (existingUser.getRole() != Role.SUPER_ADMIN) {
                log.info("Updating existing admin account role to SUPER_ADMIN: {}", adminEmail);
                existingUser.updateRole(Role.SUPER_ADMIN);
                userRepository.save(existingUser);
                log.info("Admin account role updated to SUPER_ADMIN.");
            } else {
                log.info("Admin account already exists with SUPER_ADMIN role: {}", adminEmail);
            }
        }
    }
}
