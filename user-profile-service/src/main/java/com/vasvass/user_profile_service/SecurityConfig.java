package com.vasvass.user_profile_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    // Permit all access to /hello
                    .requestMatchers("/hello").permitAll()
                    // Everything else requires authentication
                    .anyRequest().authenticated()
                )
                // Enable form login (or remove if you don't want it)
                .formLogin(Customizer.withDefaults())
                .build();
    }
}