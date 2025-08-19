package com.Api_Gateway.N;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        logger.info("🔒 Configuring Security Web Filter Chain...");

        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(
                                "/auth-service/auth/**",
                                "/vehicle-service/vehicle/**",
                                "/garage-service/garage/**",
                                "/appointment-service/appointment/**"
                        ).permitAll()
                        .anyExchange().authenticated()
                )
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));  // ✅ Apply CORS

        logger.info("✅ Security configuration applied.");
        return http.build();
    }

    // ✅ CORS Configuration Bean with Logger
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        logger.info("🌐 Configuring CORS...");

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");   // ✅ Angular frontend
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        logger.info("🔹 Allowed Origin: {}", config.getAllowedOrigins());
        logger.info("🔹 Allowed Headers: {}", config.getAllowedHeaders());
        logger.info("🔹 Allowed Methods: {}", config.getAllowedMethods());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        logger.info("✅ CORS configuration applied.");
        return source;
    }

}
