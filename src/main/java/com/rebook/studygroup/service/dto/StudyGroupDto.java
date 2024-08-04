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
    private String description;
    private int maxMemberCount;
    private int currentMemberCount;

    @Builder
    private StudyGroupDto(Long id, String name, String description, int maxMemberCount, int currentMemberCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maxMemberCount = maxMemberCount;
        this.currentMemberCount = currentMemberCount;
    }

    public static StudyGroupDto fromEntity(StudyGroupEntity studyGroupEntity) {
        return StudyGroupDto.builder()
                .id(studyGroupEntity.getId())
                .name(studyGroupEntity.getName())
                .description(studyGroupEntity.getDescription())
                .maxMemberCount(studyGroupEntity.getMaxMembers())
                .currentMemberCount(studyGroupEntity.getMembers().size())
                .build();
    }
}
