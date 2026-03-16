package com.vasvass.userprofileservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                    // Permit access to static UI resources
                    .requestMatchers("/", "/index.html", "/css/**", "/js/**").permitAll()
                    // Permit all access to user management API and public paths
                    .requestMatchers("/public/**").permitAll()
                    .requestMatchers("/api/users/**").permitAll()
                    // Protect the recommendation endpoints
                    .requestMatchers("/api/recommendations/**").authenticated()
                    .anyRequest().authenticated()
                )
                //Initiate OAuth2 login flow in a web app:
                .oauth2Login(Customizer.withDefaults());
                // Enable form login (or remove if you don't want it)
                return http.build();
    }
}