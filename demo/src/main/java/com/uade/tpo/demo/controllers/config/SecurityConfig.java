package com.uade.tpo.demo.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                .anyRequest()
                .authenticated()
                    .requestMatchers("/api/v1/auth/**").permitAll() // Permitir acceso a todas las rutas de autenticación
                    .requestMatchers("/admin/**").hasRole("ADMIN") // Rutas que solo pueden ser accesibles por ADMIN
                    .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Rutas accesibles por USER o ADMIN
                    .anyRequest().authenticated()) // Cualquier otra ruta requiere autenticación
                // Configurar la gestión de sesiones fuera del bloque authorizeHttpRequests
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }

        @SuppressWarnings("deprecation")
        @Bean
        UserDetailsService userDetailsService() {
            return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                    .username("admin")
                    .password(passwordEncoder().encode("admin"))
                    .roles("ADMIN")
                    .build(),
                User.withDefaultPasswordEncoder()
                    .username("user")
                    .password("user")
                    .roles("USER")
                    .build()
            );
        }


        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
                authenticationProvider.setUserDetailsService(userDetailsService());
                authenticationProvider.setPasswordEncoder(passwordEncoder());
                return authenticationProvider;
        }

        public AuthenticationManager authenticationManager() throws Exception {
            return new AuthenticationManager() {
                @Override
                public org.springframework.security.core.Authentication authenticate(org.springframework.security.core.Authentication authentication) {
                    return authenticationProvider().authenticate(authentication);
                }
            };
            
        }       
}
