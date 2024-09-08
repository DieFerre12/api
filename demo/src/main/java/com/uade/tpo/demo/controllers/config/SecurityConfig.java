package com.uade.tpo.demo.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(req -> req
                                                .requestMatchers("/api/v1/auth/**").permitAll() // Rutas de autenticación
                                                .requestMatchers("/error/**").permitAll() // Rutas de error
                                                .requestMatchers("/admin/**").hasRole("ADMIN") // Rutas administrativas solo para ADMIN
                                                .requestMatchers("/public/**").permitAll() // Rutas públicas

                                                .requestMatchers("/user/**").hasAnyAuthority("USER", "ADMIN") // Rutas de usuario
                                                
                                                .requestMatchers("/products/new**").permitAll() 

                                                .requestMatchers("/categories/**").permitAll() // Rutas de categorías para usuarios

                                                .requestMatchers("/ShoppingCart/**").permitAll()
                                                

                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }
}