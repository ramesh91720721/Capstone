package com.example.lms_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define a CORS configuration source. This method configures which origins, headers,
     * and methods are allowed. Here we allow http://localhost:3000.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Allow React app origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allowed HTTP methods
        configuration.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
        configuration.setAllowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply this configuration to all endpoints
        return source;
    }

    /**
     * Configure the HTTP security including CORS. The call to http.cors() uses the
     * corsConfigurationSource bean defined above. CSRF is disabled for simplicity in a REST API.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors() // enables CORS with the above configuration
            .and()
            .csrf().disable() // disable CSRF protection for simplicity (adjust as needed)
            .authorizeRequests()
                .requestMatchers("/auth/login").permitAll()  // permit all requests to /auth endpoints (login, logout, etc.)
                .requestMatchers("/api/books/**").permitAll()  // permit all requests to /auth endpoints (login, logout, etc.)
                .requestMatchers("/api/**").permitAll()  // permit all requests to /auth endpoints (login, logout, etc.)
                .requestMatchers("/books/**").permitAll()  // permit all requests to /auth endpoints (login, logout, etc.)
                .anyRequest().authenticated(); // all other requests require authentication

        return http.build();
    }
}

 