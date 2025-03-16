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
                .authorizeHttpRequests(authorize -> authorize
                    // Permit all access to the root URL "/" and the user registration URL "/api/users"
                    .requestMatchers("/public//**").permitAll() 
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