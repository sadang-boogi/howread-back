package com.rebook.studygroup.controller.response;

import com.rebook.common.schema.ListResponse;
import com.rebook.studygroup.service.dto.StudyGroupDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Collectors;

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
                                .collect(Collectors.toList())
                )

        );
    }
}
