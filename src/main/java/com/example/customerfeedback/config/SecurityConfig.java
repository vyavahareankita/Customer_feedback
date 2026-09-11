
package com.example.customerfeedback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {

        return (request, response, authentication) -> {

            boolean isAdmin = authentication.getAuthorities()
                    .stream()
                    .anyMatch(authority ->
                            authority.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin) {
                response.sendRedirect("/admin");
            } else {
                response.sendRedirect("/feedback");
            }
        };
    }

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationSuccessHandler authenticationSuccessHandler)
            throws Exception {

        http

            // Authorization
            .authorizeHttpRequests(auth -> auth

                // Public pages
                .requestMatchers(
                        "/",
                        "/login",
                        "/register",
                        "/css/**",
                        "/error"
                ).permitAll()

                // Admin pages
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")

                // User pages
                .requestMatchers("/feedback/**")
                .hasRole("USER")

                // Everything else requires login
                .anyRequest()
                .authenticated()
            )

            // Login
            .formLogin(form -> form

                .loginPage("/login")

                .successHandler(authenticationSuccessHandler)

                .permitAll()
            )

            // Logout
            .logout(logout -> logout

                .logoutUrl("/logout")

                .logoutSuccessUrl("/login?logout")

                .invalidateHttpSession(true)

                .clearAuthentication(true)

                .deleteCookies("JSESSIONID")

                .permitAll()
            );

        return http.build();
    }
}

