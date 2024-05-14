package com.rebook.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.rebook.user.config.OAuth2ClientProperties;
import com.rebook.user.service.dto.LoggedInUser;
import com.rebook.user.service.dto.UserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final OAuth2ClientProperties oauth2Properties;
    private final RestTemplate restTemplate = new RestTemplate();
    private final UserService userService;


    public void socialLogin(String code, String registrationId) {
        String accessToken = getAccessToken(code, registrationId);
        UserCommand userCommand = getUserProfile(accessToken, registrationId);
        LoggedInUser user = userService.createUser(userCommand);
    }

    private String getAccessToken(String authorizationCode, String registrationId) {
        OAuth2ClientProperties.Registration registration = oauth2Properties.getRegistration().get(registrationId);
        OAuth2ClientProperties.Provider provider = oauth2Properties.getProvider().get(registrationId);

        String clientId = registration.getClientId();
        String clientSecret = registration.getClientSecret();
        String redirectUri = registration.getRedirectUri();
        String tokenUri = provider.getTokenUri();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }


    private JsonNode getUserResource(String accessToken, String registrationId) {
        OAuth2ClientProperties.Provider provider = oauth2Properties.getProvider().get(registrationId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(provider.getUserInfoUri(), HttpMethod.GET, entity, JsonNode.class).getBody();
    }

    private UserCommand getUserProfile(String accessToken, String registrationId) {
        JsonNode userResource = getUserResource(accessToken, registrationId);
        String id = userResource.get("sub").asText();
        String email = userResource.get("email").asText();
        String name = userResource.get("name").asText();
        return UserCommand.builder()
                .name(name)
                .email(email)
                .socialId(id)
                .socialType(registrationId.toUpperCase()).build();
    }
}