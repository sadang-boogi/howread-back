package com.rebook.common.domain;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app.cors")
@Component
public class CorsProperties {
    private List<String> allowHosts;
}
