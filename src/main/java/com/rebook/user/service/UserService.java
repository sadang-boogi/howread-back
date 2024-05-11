package com.rebook.user.service;

import com.rebook.user.domain.Role;
import com.rebook.user.domain.SocialType;
import com.rebook.user.domain.UserEntity;
import com.rebook.user.service.dto.LoggedInUser;
import com.rebook.user.service.dto.UserCommand;
import com.rebook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public LoggedInUser createUser(UserCommand userCommand) {
        String email = userCommand.getEmail();
        Optional<UserEntity> existingUser = userRepository.findByEmail(email);

        //존재하는 회원일 경우
        if (existingUser.isPresent()) {
            return LoggedInUser.fromEntity(existingUser.get());
        }

        //아닐 경우 신규 DB에 저장
        UserEntity newUser = UserEntity.builder()
                .email(email)
                .nickname(userCommand.getName())
                .socialType(SocialType.valueOf(userCommand.getSocialType()))
                .socialId(userCommand.getSocialId())
                .role(Role.USER)
                .build();
        return LoggedInUser.fromEntity(userRepository.save(newUser));
    }
}
