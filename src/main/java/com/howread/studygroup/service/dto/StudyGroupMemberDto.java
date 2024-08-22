package com.howread.studygroup.service.dto;

import com.howread.studygroup.domain.StudyGroupMemberEntity;
import com.howread.studygroup.domain.StudyGroupMemberRole;

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
    private UserInfoDto user;
    private StudyGroupMemberRole role;

    public static StudyGroupMemberDto from(StudyGroupMemberEntity member) {
        return StudyGroupMemberDto.builder()
                .user(UserInfoDto.from(member.getUser()))
                .id(member.getId())
                .role(member.getRole())
                .build();
    }
}
