package com.rebook.studygroup.service.dto;

import com.rebook.studygroup.domain.StudyGroupMemberEntity;
import com.rebook.studygroup.domain.StudyGroupMemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyGroupMemberDto {
    private Long id;
    private String nickname;
    private StudyGroupMemberRole role;
    private String avatarUrl;

    public static StudyGroupMemberDto fromEntity(StudyGroupMemberEntity member) {
        return StudyGroupMemberDto.builder()
                .id(member.getId())
                .nickname(member.getUser().getNickname())
                .role(member.getRole())
                .avatarUrl(member.getUser().getAvatarUrl())
                .build();
    }
}
