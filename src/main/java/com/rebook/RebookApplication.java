package com.rebook;

import com.rebook.common.config.SecretManagerInitializer;
import com.rebook.common.config.SecretManagerService;
import com.rebook.common.domain.CorsProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(CorsProperties.class)
@OpenAPIDefinition(
        servers = {
                @Server(url = "/", description = "Default Server URL")
        }
)
@SpringBootApplication
public class RebookApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RebookApplication.class);
        application.addInitializers(new SecretManagerInitializer(secretManagerService()));
        application.run(args);
    }

    @Bean
    public static SecretManagerService secretManagerService() {
        return new SecretManagerService();
    }

}
