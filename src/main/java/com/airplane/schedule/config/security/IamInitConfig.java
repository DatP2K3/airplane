package com.airplane.schedule.config.security;

import com.airplane.schedule.model.Role;
import com.airplane.schedule.model.User;
import com.airplane.schedule.repository.RoleRepository;
import com.airplane.schedule.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class IamInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_EMAIL = "admin@gmail.com";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
                roleRepository.save(Role.builder()
                        .name("ROLE_USER")
                        .build());

                Role adminRole = roleRepository.save(Role.builder()
                        .name("ROLE_ADMIN")
                        .build());

                User user = User.builder()
                        .email(ADMIN_EMAIL)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .build();
                adminRole.assignRoleToUser(user);
                roleRepository.save(adminRole);
                log.warn("admin@gmail.com user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}