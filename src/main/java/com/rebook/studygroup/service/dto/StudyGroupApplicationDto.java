package com.rebook.studygroup.service.dto;

import com.rebook.studygroup.domain.StudyGroupApplicationStatus;
import com.rebook.studygroup.domain.StudyGroupMemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudyGroupApplicationDto {

    private Long id;
    private Long studyGroupId;
    private Long userId;
    private StudyGroupApplicationStatus status;

    @Builder
    public StudyGroupApplicationDto(Long id, Long studyGroupId, Long userId, StudyGroupApplicationStatus status) {
        this.id = id;
        this.studyGroupId = studyGroupId;
        this.userId = userId;
        this.status = status;
    }

    public static StudyGroupApplicationDto fromEntity(StudyGroupMemberEntity studyGroupMember) {
        return StudyGroupApplicationDto.builder()
                .id(studyGroupMember.getId())
                .studyGroupId(studyGroupMember.getStudyGroup().getId())
                .userId(studyGroupMember.getUser().getId())
                .status(studyGroupMember.getStatus())
                .build();
    }
}
