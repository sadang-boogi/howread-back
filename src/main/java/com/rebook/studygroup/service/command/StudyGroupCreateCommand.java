package com.rebook.studygroup.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudyGroupCreateCommand {

    private String name;
    private Integer maxMembers;
    private Long leaderId;

    @Builder
    public StudyGroupCreateCommand(String name, Integer maxMembers, Long leaderId) {
        this.name = name;
        this.maxMembers = maxMembers;
        this.leaderId = leaderId;
    }
}
