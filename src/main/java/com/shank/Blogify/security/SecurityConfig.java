package com.shank.Blogify.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] WHITELIST = {
            "/",
            "/register",
            "/login",
            "/forgot-password",
            "/change-password",
            "/reset-password/**",

            "/upload-image",        // CKEditor upload
            "/uploads/**",          // Uploaded images

            "/console/**",
            "/post/**",

            "/css/**",
            "/js/**",
            "/images/**",
            "/fonts/**"
    };

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 🔥 FIX: Disable CSRF for upload-image ALSO
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(
                    "/db-console/**",
                    "/upload-image"
                )
            )

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(WHITELIST).permitAll()
                .requestMatchers("/profile/**").authenticated()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/test").hasAuthority("ACCESS_ADMIN_PANEL")
                .requestMatchers("/editor/**").hasAnyRole("ADMIN", "EDITOR")
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
            )

            .rememberMe(rememberMe -> rememberMe
                .rememberMeParameter("remember-me")
                .key("my-secret-key") // must be stable
            )

            .httpBasic(httpBasic -> httpBasic.disable())

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            )

            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
            );

        return http.build();
    }
}