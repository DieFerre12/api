package com.uade.tpo.demo.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.uade.tpo.demo.entity.Role;

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
                                        .requestMatchers("/api/v1/auth/").permitAll() 
                                        .requestMatchers("/admin/").hasRole("ADMIN") 
                                        .requestMatchers("/user/").hasRole("USER") 
                                        .requestMatchers("/users/").hasRole("ADMIN") 
                                        .requestMatchers("/order/").hasAnyAuthority("USER", "ADMIN") 
                                        .requestMatchers(HttpMethod.GET,"/products/").hasAnyAuthority("USER", "ADMIN")
                                        .requestMatchers("/products/").hasAnyAuthority("ADMIN") 
                                        .requestMatchers(HttpMethod.GET,"/categories/").hasAnyAuthority("USER", "ADMIN")
                                        .requestMatchers("/categories/").hasAnyAuthority("ADMIN") 
                                        .requestMatchers("/ShoppingCart/").hasAnyAuthority("ADMIN")
                                        .requestMatchers("/ShoppingCart/user/**").hasAnyAuthority("USER", "ADMIN")
                                        .requestMatchers("/order/create").hasAnyAuthority("USER", "ADMIN")
                                                
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }
}