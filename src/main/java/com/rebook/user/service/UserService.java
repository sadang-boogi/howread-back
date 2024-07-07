package com.rebook.user.service;

import com.rebook.common.exception.NotFoundException;
import com.rebook.review.domain.ReviewEntity;
import com.rebook.user.domain.Role;
import com.rebook.user.domain.UserEntity;
import com.rebook.user.repository.UserRepository;
import com.rebook.user.service.command.UserUpdateCommand;
import com.rebook.user.service.dto.AuthClaims;
import com.rebook.user.service.dto.UserCommand;
import com.rebook.user.service.dto.UserDto;
import com.rebook.user.util.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
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

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(UserDto::fromEntity)
                .toList();
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

        user.update(
                command.getNickname()
        );

        return UserDto.fromEntity(user);
    }

    @Transactional
    public void softDelete(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자 삭제 실패.", "사용자를 찾을 수 없습니다."));

        user.getReviews().forEach(ReviewEntity::softDelete);
        user.softDelete();
    }
}
