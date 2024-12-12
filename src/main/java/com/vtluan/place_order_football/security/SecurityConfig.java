package com.vtluan.place_order_football.security;

import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;

import com.vtluan.place_order_football.service.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${vtluan-jwt-screkey}")
    private String jwtKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public DaoAuthenticationProvider authenticationProvider(CustomDetailService
    // customDetailService) {
    // DaoAuthenticationProvider daoAuthenticationProvider = new
    // DaoAuthenticationProvider();
    // daoAuthenticationProvider.setUserDetailsService(customDetailService);
    // daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    // return daoAuthenticationProvider;
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.disable())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Các nguồn
                                                                                 // cho phép
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Các phương thức
                                                                                                   // HTTP
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Headers cho phép
        configuration.setExposedHeaders(Arrays.asList("Authorization")); // Headers được lộ ra cho client
        configuration.setAllowCredentials(true); // Cho phép cookies hoặc thông tin xác thực
        configuration.setMaxAge(3600L); // Cache trong 1 giờ

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng cho tất cả các đường dẫn
        return source;
    }

    public SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, JwtUtil.macAlgorithm.getName());
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        // Tạo JwtEncoder sử dụng HMAC
        return new NimbusJwtEncoder(new ImmutableSecret(getSecretKey()));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // create nimbusdecode -> call method decode

        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(JwtUtil.macAlgorithm)
                .build();
        return (token) -> {
            try {
                return decoder.decode(token);
            } catch (Exception e) {
                throw e;
            }
        };
    }

}
