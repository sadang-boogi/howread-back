package com.howread.studygroup.controller.request;

import com.howread.studygroup.service.command.StudyGroupCreateCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudyGroupCreateRequest {
    private String name;
    private String description;
    private int maxMembers;

    public StudyGroupCreateCommand toCommand() {
        return new StudyGroupCreateCommand(name, description, maxMembers);
    }
}
