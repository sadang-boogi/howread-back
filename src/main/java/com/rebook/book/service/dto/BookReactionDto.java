package com.rebook.book.service.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class BookReactionDto {

    private boolean isFollowedByMe; // 사용자가 팔로우했는지 여부
    private boolean isLikedByMe; // 사용자가 좋아요를 눌렀는지 여부

    @Builder
    public BookReactionDto(boolean isFollowedByMe, boolean isLikedByMe) {
        this.isFollowedByMe = isFollowedByMe;
        this.isLikedByMe = isLikedByMe;
    }
}
