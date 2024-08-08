package com.rebook.studygroup.controller.response;

import com.rebook.common.schema.ListResponse;
import com.rebook.studygroup.service.dto.StudyGroupDto;
import com.rebook.studygroup.service.dto.StudyGroupMemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
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

    public static StudyGroupResponse from(StudyGroupDto studyGroupDto, List<StudyGroupMemberDto> studyGroupMemberDtoList) {
        return new StudyGroupResponse(
                studyGroupDto.getId(),
                studyGroupDto.getName(),
                studyGroupDto.getDescription(),
                studyGroupDto.getMaxMembers(),
                studyGroupDto.getCurrentMembers(),

                new ListResponse<>(
                        studyGroupMemberDtoList == null ? List.of() : studyGroupMemberDtoList.stream() // 빈 리스트면 null
                                .map(StudyGroupMemberResponse::from)
                                .collect(Collectors.toList())
                )
        );
    }
}
