package com.rebook.studygroup.controller.response;

import com.rebook.studygroup.domain.StudyGroupMemberRole;
import com.rebook.studygroup.service.dto.StudyGroupMemberDto;

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
