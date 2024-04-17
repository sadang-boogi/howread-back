package com.rebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(RebookApplication.class, args);
    }

}
