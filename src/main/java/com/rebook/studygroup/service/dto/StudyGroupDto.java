package com.rebook.studygroup.service.dto;

import com.rebook.studygroup.domain.StudyGroupEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudyGroupDto {

    private Long id;

    private String name;

    private Integer maxMembers;

    private Long leaderId;

    @Builder
    public StudyGroupDto(Long id, String name, Integer maxMembers, Long leaderId) {
        this.id = id;
        this.name = name;
        this.maxMembers = maxMembers;
        this.leaderId = leaderId;
    }

    public static StudyGroupDto fromEntity(StudyGroupEntity studyGroup) {
        return StudyGroupDto.builder()
                .id(studyGroup.getId())
                .name(studyGroup.getName())
                .maxMembers(studyGroup.getMaxMembers())
                .leaderId(studyGroup.getLeader().getId())
                .build();
    }
}
