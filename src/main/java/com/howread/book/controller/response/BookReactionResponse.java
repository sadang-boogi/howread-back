package com.howread.book.controller.response;

import com.howread.book.service.dto.BookReactionDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class BookReactionResponse {

    private boolean isFollowedByMe; // 사용자가 이 책을 팔로우했는지 여부
    private boolean isLikedByMe;    // 사용자가 이 책을 좋아요 했는지 여부

    public static BookReactionResponse from(BookReactionDto reactionDto) {
        return new BookReactionResponse(
                reactionDto.isFollowedByMe(),
                reactionDto.isLikedByMe()
        );
    }
}
