package com.rebook.studygroup.controller.response;

import com.rebook.studygroup.domain.StudyGroupApplicationStatus;
import com.rebook.studygroup.service.dto.StudyGroupApplicationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudyGroupApplicationResponse {

    private Long id;
    private Long studyGroupId;
    private Long userId;
    private StudyGroupApplicationStatus status;

    @Builder
    public StudyGroupApplicationResponse(Long id, Long studyGroupId, Long userId, StudyGroupApplicationStatus status) {
        this.id = id;
        this.studyGroupId = studyGroupId;
        this.userId = userId;
        this.status = status;
    }

    public static StudyGroupApplicationResponse from(StudyGroupApplicationDto dto) {
        return StudyGroupApplicationResponse.builder()
                .id(dto.getId())
                .studyGroupId(dto.getStudyGroupId())
                .userId(dto.getUserId())
                .status(dto.getStatus())
                .build();
    }
}
