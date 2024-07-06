package com.rebook.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Slf4j
@Component
public class SecretManagerService {
    public void setSecret() {
        String secretName = "dev/rebook/secrets";
        Region region = Region.of("ap-northeast-2");

        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw e;
        }

        String secretString = getSecretValueResponse.secretString();

        ObjectMapper objectMapper = new ObjectMapper();
        Secrets secrets;

        try {
            secrets = objectMapper.readValue(secretString, Secrets.class);
        } catch (Exception e) {
            throw new RuntimeException("시크릿 가져오기 실패 : ", e);
        }

        // environment 변수 설정
        System.setProperty("GOOGLE_OAUTH_CLIENT_SECRET", secrets.getGoogleOauthClientSecret());
        System.setProperty("DB_PASSWORD", secrets.getDbPassword());
        System.setProperty("JWT_SECRET_KEY", secrets.getJwtSecretKey());

    }
}
