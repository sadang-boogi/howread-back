package com.rebook.common.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(

)
@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()
                        .components(new Components())
                        .info(apiInfo());
        }

        private Info apiInfo() {
                return new Info()
                        .title("reBook API 명세서")
                        .description("rebook 프로젝트에 사용되는 API 명세서")
                        .version("v1");
        }
}