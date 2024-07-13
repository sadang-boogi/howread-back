package com.rebook.studygroup.controller.response;

import com.rebook.studygroup.service.dto.StudyGroupDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudyGroupResponse {

    private Long id;
    private String name;
    private int maxMembers;
    private Long leaderId;

    public static StudyGroupResponse fromDto(StudyGroupDto studyGroupDto) {
        return StudyGroupResponse.builder()
                .id(studyGroupDto.getId())
                .name(studyGroupDto.getName())
                .maxMembers(studyGroupDto.getMaxMembers())
                .leaderId(studyGroupDto.getLeaderId())
                .build();
    }

    @Builder
    public StudyGroupResponse(Long id, String name, int maxMembers, Long leaderId) {
        this.id = id;
        this.name = name;
        this.maxMembers = maxMembers;
        this.leaderId = leaderId;
    }
}
