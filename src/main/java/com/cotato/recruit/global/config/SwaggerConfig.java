package com.cotato.recruit.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger UI 상단에 보이는 API 문서의 기본 정보(제목, 설명, 버전)를 등록한다.
 * 개별 API 설명은 각 컨트롤러의 어노테이션이 맡고, 이 클래스는 문서 메타 정보만 다룬다.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("COTATO Recruit API")
                        .description("코테이토 리쿠르트 API 문서")
                        .version("v1.0.0"));
    }
}
