package com.howread.user.service;

import com.howread.common.exception.ExceptionCode;
import com.howread.common.exception.NotFoundException;
import com.howread.image.domain.ImageEntity;
import com.howread.image.repository.ImageRepository;
import com.howread.user.domain.Role;
import com.howread.user.domain.UserEntity;
import com.howread.user.repository.UserRepository;
import com.howread.user.service.command.UserUpdateCommand;
import com.howread.user.service.dto.AuthClaims;
import com.howread.user.service.dto.UserCommand;
import com.howread.user.service.dto.UserDto;
import com.howread.user.util.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public AuthClaims createUser(UserCommand userCommand) {
        String socialId = userCommand.getSocialId();
        Optional<UserEntity> existingUser = userRepository.findBySocialId(socialId);

        // 존재하는 회원일 경우
        if (existingUser.isPresent()) {
            return AuthClaims.fromEntity(existingUser.get());
        }

        // 아닐 경우 신규 DB에 저장
        UserEntity newUser = UserEntity.builder()
                .email(userCommand.getEmail())
                .nickname(userCommand.getName())
                .socialType(SocialType.valueOf(userCommand.getSocialType()))
                .socialId(userCommand.getSocialId())
                .role(Role.USER)
                .avatarUrl(null) // 소셜 로그인 회원 가입시 프로필 사진 추후 등록
                .build();

        return AuthClaims.fromEntity(userRepository.save(newUser));
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자 정보 조회 실패", "해당 유저가 존재하지 않습니다."));
        return UserDto.fromEntity(user);
    }

    @Transactional
    public UserDto updateUser(UserUpdateCommand command) {
        UserEntity user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new NotFoundException("사용자 정보 수정 실패", "해당 유저가 존재하지 않습니다."));

        ImageEntity image = imageRepository.findById(command.getAvatarImageId())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_IMAGE_ID));

        user.update(command.getNickname(), image.getUrl());

        return UserDto.fromEntity(user);
    }
}
