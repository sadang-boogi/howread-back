package com.howread.studygroup.controller.response;

import com.howread.studygroup.domain.StudyGroupMemberRole;
import com.howread.studygroup.service.dto.StudyGroupMemberDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudyGroupMemberResponse {

    private Long id;
    private UserInfoResponse userInfoResponse;
    private StudyGroupMemberRole role;

    public static StudyGroupMemberResponse from(StudyGroupMemberDto studyGroupMemberDto) {
        return new StudyGroupMemberResponse(
                studyGroupMemberDto.getId(),
                UserInfoResponse.from(studyGroupMemberDto.getUser()),
                studyGroupMemberDto.getRole()
        );
    }

}
