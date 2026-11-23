package com.cotato.recruit.global.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * 브라우저의 다른 출처 요청 중 허용할 출처·메서드를 정한다.
 * {@link SecurityConfig} 의 CORS 필터가 이 설정을 가져다 쓰며, 인증·인가 규칙은 여기서 다루지 않는다.
 */
@Configuration
public class CorsConfig {

    // 로컬 프론트엔드 주소만 허용한다. 배포 도메인이 정해지면 여기에 추가한다.
    private static final List<String> ALLOWED_ORIGINS = List.of("http://localhost:3000");

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(ALLOWED_ORIGINS);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
