package com.rebook.studygroup.service.dto;

import com.rebook.studygroup.domain.StudyGroupEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class StudyGroupDto {
    private Long id;
    private String name;
    private String description;
    private int maxMembers;
    private int currentMembers;
    private List<StudyGroupMemberDto> members;

    @Builder
    private StudyGroupDto(Long id, String name, String description, int maxMembers, int currentMembers, List<StudyGroupMemberDto> members) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maxMembers = maxMembers;
        this.currentMembers = currentMembers;
        this.members = members;
    }

    public static StudyGroupDto fromEntity(StudyGroupEntity studyGroupEntity) {
        return StudyGroupDto.builder()
                .id(studyGroupEntity.getId())
                .name(studyGroupEntity.getName())
                .description(studyGroupEntity.getDescription())
                .maxMembers(studyGroupEntity.getMaxMembers())
                .currentMembers(studyGroupEntity.getMembers().size())
                .members(
                        studyGroupEntity.getMembers().stream()
                                .map(StudyGroupMemberDto::from)
                                .toList()
                )
                .build();
    }
}
