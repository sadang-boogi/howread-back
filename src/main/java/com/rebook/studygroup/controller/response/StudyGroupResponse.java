package com.rebook.studygroup.controller.response;

import com.rebook.studygroup.service.dto.StudyGroupDto;
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

    public static StudyGroupResponse from(StudyGroupDto studyGroupDto) {
        return new StudyGroupResponse(
                studyGroupDto.getId(),
                studyGroupDto.getName(),
                studyGroupDto.getDescription(),
                studyGroupDto.getMaxMembers(),
                studyGroupDto.getCurrentMembers());
    }
}
