package com.rebook;

import com.rebook.common.domain.CorsProperties;
import com.rebook.user.service.dto.AuthClaims;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.rebook.jwt.JwtUtil;

import org.springframework.boot.ApplicationRunner;

import java.time.Instant;


@SpringBootApplication
public class TokenGenerator implements ApplicationRunner {

    // TODO: TokenEncoder
    @Autowired
    private JwtUtil jwtUtil;

    public static void main(String[] args) {
        SpringApplication.run(TokenGenerator.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        AuthClaims auth = AuthClaims.builder()
                .userId(8L)
                .build();

        String token = jwtUtil.createToken(auth, Instant.now());
        System.out.println(token);
    }
}
