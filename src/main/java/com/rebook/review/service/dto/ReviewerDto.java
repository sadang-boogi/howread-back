package com.rebook.review.service.dto;

import com.rebook.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewerDto {
    private Long id;
    private String name;

    public static ReviewerDto fromUserEntity(UserEntity user) {
        return new ReviewerDto(user.getId(), user.getNickname());
    }
}
