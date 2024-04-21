package com.rebook.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "reBook API 명세서",
                description = "rebook 프로젝트에 사용되는 API 명세서",
                version = "v1"
        ),
        tags = {
                @Tag(name = "Book", description = "도서 등록"),
                @Tag(name = "Review", description = "리뷰 등록")
        }
)
@Configuration
public class SwaggerConfig {


}
