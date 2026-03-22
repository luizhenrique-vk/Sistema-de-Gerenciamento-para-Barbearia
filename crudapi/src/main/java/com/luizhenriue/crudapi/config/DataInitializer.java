package com.luizhenriue.crudapi.config;

import com.luizhenriue.crudapi.entity.User;
import com.luizhenriue.crudapi.model.Role;
import com.luizhenriue.crudapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedAdminUser() {
        return args -> {
            if (!userRepository.existsByEmail("admin@barbearia.com")) {
                userRepository.save(User.builder()
                    .name("Administrador")
                    .email("admin@barbearia.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build());
            }
        };
    }
}
