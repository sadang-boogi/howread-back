package com.rebook.studygroup.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudyGroupCommand {

    private String name;
    private Integer maxMembers;
    private Long leaderId;

    @Builder
    public StudyGroupCommand(String name, Integer maxMembers, Long leaderId) {
        this.name = name;
        this.maxMembers = maxMembers;
        this.leaderId = leaderId;
    }
}
