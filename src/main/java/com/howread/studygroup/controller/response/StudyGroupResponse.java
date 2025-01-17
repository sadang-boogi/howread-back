package com.howread.studygroup.controller.response;

import com.howread.common.schema.ListResponse;
import com.howread.studygroup.service.dto.StudyGroupDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudyGroupResponse {

    private Long id;
    private String name;
    private String description;
    private int maxMembers;
    private int currentMembers;
    private ListResponse<StudyGroupMemberResponse> studyGroupMemberResponseListResponse;

    public static StudyGroupResponse from(StudyGroupDto studyGroupDto) {
        return new StudyGroupResponse(
                studyGroupDto.getId(),
                studyGroupDto.getName(),
                studyGroupDto.getDescription(),
                studyGroupDto.getMaxMembers(),
                studyGroupDto.getCurrentMembers(),
                new ListResponse<>(
                        studyGroupDto.getMembers().stream()
                                .map(StudyGroupMemberResponse::from)
                                .toList()
                )

        );
    }
}
