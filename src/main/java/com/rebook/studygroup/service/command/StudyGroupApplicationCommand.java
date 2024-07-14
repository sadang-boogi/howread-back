package com.rebook.studygroup.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudyGroupApplicationCommand {

    private Long studyGroupId;
    private Long userId;

    @Builder
    public StudyGroupApplicationCommand(Long studyGroupId, Long userId) {
        this.studyGroupId = studyGroupId;
        this.userId = userId;
    }
}
