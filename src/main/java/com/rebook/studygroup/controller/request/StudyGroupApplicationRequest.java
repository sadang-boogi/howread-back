package com.rebook.studygroup.controller.request;

import com.rebook.studygroup.service.command.StudyGroupApplicationCommand;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudyGroupApplicationRequest {

    @NotNull
    private Long studyGroupId;

    @NotNull
    private Long userId;

    @Builder
    public StudyGroupApplicationRequest(Long studyGroupId, Long userId) {
        this.studyGroupId = studyGroupId;
        this.userId = userId;
    }

    public StudyGroupApplicationCommand toCommand(Long studyGroupId, Long userId) {
        return StudyGroupApplicationCommand.builder()
                .studyGroupId(studyGroupId)
                .userId(userId)
                .build();
    }
}
