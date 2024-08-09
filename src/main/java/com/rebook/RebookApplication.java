package com.rebook;

import com.rebook.common.domain.CorsProperties;
import com.rebook.jwt.JwtUtil;
import com.rebook.user.service.dto.AuthClaims;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.Instant;

@EnableConfigurationProperties(CorsProperties.class)
@OpenAPIDefinition(
        servers = {
                @Server(url = "/", description = "Default Server URL")
        }
)
@SpringBootApplication
public class RebookApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RebookApplication.class, args);
    }


    @Autowired
    JwtUtil jwtUtil;

    @Override
    public void run(String... args) throws Exception {
        String token = jwtUtil.createToken(new AuthClaims(1L), Instant.now());
        System.out.println("token = " + token);
    }
}
