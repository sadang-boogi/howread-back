package com.howread.review.service.dto;

import com.howread.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewerDto {
    private Long id;
    private String name;
    private String avatarUrl;

    public static ReviewerDto fromUserEntity(UserEntity user) {
        return new ReviewerDto(user.getId(), user.getNickname(), user.getAvatarUrl());
    }
}
