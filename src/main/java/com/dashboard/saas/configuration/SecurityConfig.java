package com.dashboard.saas.configuration;

import com.dashboard.saas.security.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

        private final AuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(AuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS //  No sessions, we are using JWTs .. we are using stateless
                        )
                )
                .authorizeHttpRequests(auth -> auth



//                        .requestMatchers(
//                                "/api/v1/redis/test"  // TO Hardcode to Ignore this endpoint
//                        ).denyAll()

                                // PUBLIC APIs
                        .requestMatchers(
                                "/api/v1/authentication/register",
                                "/api/v1/authentication/login",
                                "/api/v1/authentication/refresh-token",
                                "/api/v1/authentication/logout",
                                "/api/v1/authentication/verify-otp",
                                "/api/v1/redis/test",
                                "/api/v1/authentication/resend-otp",
                                "api/v1/authentication/user/{id}",
                                "api/v1/authentication/update-user/{userId}",
                                "api/v1/authentication/sessions/{userId}"
                        ).permitAll()

                        // SECURED APIs
                        .anyRequest().authenticated()
                )

                // JWT FILTER
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
