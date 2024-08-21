package com.howread;

import com.howread.common.domain.CorsProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(CorsProperties.class)
@OpenAPIDefinition(
        servers = {
                @Server(url = "/", description = "Default Server URL")
        }
)
@SpringBootApplication
public class HowReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(HowReadApplication.class, args);
    }

}
