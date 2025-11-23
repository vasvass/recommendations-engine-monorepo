package com.vasvass.recommendationservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for REST API
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions for JWT
            .authorizeHttpRequests(authorize -> authorize
                // Public endpoints
                .requestMatchers("/api/recommendations/health").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                // Protected endpoints - require authentication
                .requestMatchers("/api/recommendations/**").authenticated()
                .requestMatchers("/api/admin/**").authenticated()
                .anyRequest().authenticated()
            )
            // Configure OAuth2 Resource Server (JWT)
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> {}) // JWT validation will use issuer-uri from application.properties
            );
        
        return http.build();
    }
}

