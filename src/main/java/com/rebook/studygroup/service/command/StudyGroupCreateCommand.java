package com.rebook.studygroup.service.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudyGroupCreateCommand {
    private String name;
    private String description;
    private int maxMembers;
}
