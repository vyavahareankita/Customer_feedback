package com.example.customerfeedback.config;

import com.example.customerfeedback.model.Role;
import com.example.customerfeedback.model.User;
import com.example.customerfeedback.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedAdmin(UserRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (!repository.existsByEmail("admin@gmail.com")) {
                repository.save(new User(
                    "Administrator",
                    "admin@gmail.com",
                    encoder.encode("admin123"),
                    Role.ADMIN
                ));
            }
        };
    }
}
