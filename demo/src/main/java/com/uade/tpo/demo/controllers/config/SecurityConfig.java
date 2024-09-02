package com.uade.tpo.demo.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.uade.tpo.demo.entity.Role;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

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
                                                .requestMatchers("/categories/**").hasAnyAuthority(Role.USER.name()) // Rutas de categorías para usuarios
                                                .requestMatchers("/admin/**").hasRole("ADMIN") // Rutas administrativas solo para ADMIN
                                                .requestMatchers("/public/**").permitAll() // Rutas públicas
                                                .requestMatchers("/user/**").hasAnyAuthority("USER", "ADMIN") // Rutas de usuario
                                                .requestMatchers("/products/**").permitAll() // Productos accesibles por GET sin autenticación
                                                .requestMatchers( "/products/**").permitAll() // Solo ADMIN puede hacer POST en productos
                                                .requestMatchers("/categories/**").hasAnyAuthority(Role.USER.name())
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
