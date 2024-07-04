package com.rebook.user.service;

import com.rebook.user.domain.Role;
import com.rebook.user.domain.UserEntity;
import com.rebook.user.service.dto.AuthClaims;
import com.rebook.user.service.dto.UserCommand;
import com.rebook.user.repository.UserRepository;
import com.rebook.user.util.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public AuthClaims createUser(UserCommand userCommand) {
        String socialId = userCommand.getSocialId();
        Optional<UserEntity> existingUser = userRepository.findBySocialId(socialId);

        //존재하는 회원일 경우
        if (existingUser.isPresent()) {
            return AuthClaims.fromEntity(existingUser.get());
        }

        //아닐 경우 신규 DB에 저장
        UserEntity newUser = UserEntity.builder()
                .email(userCommand.getEmail())
                .nickname(userCommand.getName())
                .socialType(SocialType.valueOf(userCommand.getSocialType()))
                .socialId(userCommand.getSocialId())
                .role(Role.USER)
                .build();

        return AuthClaims.fromEntity(userRepository.save(newUser));
    }
}