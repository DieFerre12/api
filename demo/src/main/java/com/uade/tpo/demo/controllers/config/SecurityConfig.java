package com.uade.tpo.demo.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

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
                                                .requestMatchers("/api/v1/auth/authenticate").permitAll()
                                                .requestMatchers("/api/v1/auth/register").permitAll()
                                                .requestMatchers("/admin/").hasRole("ADMIN")
                                                .requestMatchers("/users/all").hasAnyAuthority("ADMIN","USER")
                                                .requestMatchers("/order/").hasAnyAuthority("USER", "ADMIN")
                                                .requestMatchers("/products/**").permitAll()
                                                .requestMatchers("/products/**").hasAnyAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.GET, "/categories/").hasAnyAuthority("USER", "ADMIN")
                                                .requestMatchers("/categories/create").hasAnyAuthority("ADMIN")
                                                .requestMatchers("/categories").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/shoppingCart/user/**").hasAnyAuthority("USER", "ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/shoppingCart/user/{userId}/addProduct").permitAll()
                                                .requestMatchers(HttpMethod.PUT, "/shoppingCart/user/{userId}/updateProduct/{model}/{size}").hasAnyAuthority("ADMIN", "USER")
                                                .requestMatchers(HttpMethod.DELETE, "/shoppingCart/user/{userId}/removeProduct/{model}/{size}").hasAnyAuthority("ADMIN", "USER")
                                                .requestMatchers(HttpMethod.DELETE, "/shoppingCart/user/{userId}/clearCart").hasAnyAuthority("ADMIN", "USER")
                                                .requestMatchers("/shoppingCart/getAll").hasAnyAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.GET, "/order/**").hasAnyAuthority("ADMIN")
                                                .requestMatchers(HttpMethod.POST,"/order/create").hasAnyAuthority("USER", "ADMIN")
                                                .requestMatchers("/image/**").hasAnyAuthority("ADMIN")
                                                .anyRequest().authenticated())
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authenticationProvider(authenticationProvider)
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                        return http.build();

                                                        }
}