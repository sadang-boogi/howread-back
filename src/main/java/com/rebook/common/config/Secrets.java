package com.rebook.common.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Secrets {

    @JsonProperty("GOOGLE_OAUTH_CLIENT_SECRET")
    private String googleOauthClientSecret;

    @JsonProperty("DB_PASSWORD")
    private String dbPassword;

    @JsonProperty("JWT_SECRET_KEY")
    private String jwtSecretKey;

}
