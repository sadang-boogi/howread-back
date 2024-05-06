package com.rebook.user.service;

import com.rebook.user.dto.CustomOAuth2User;
import com.rebook.user.dto.GoogleResponse;
import com.rebook.user.dto.OAuth2Response;
import com.rebook.user.dto.UserCommand;
import com.rebook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {

            return null;
        }
        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
        UserCommand userCommand = new UserCommand();
        userCommand.setEmail(oAuth2Response.getEmail());
        userCommand.setName(oAuth2Response.getName());
        userCommand.setRole("ROLE_USER");

        return new CustomOAuth2User(userCommand);

    }
}