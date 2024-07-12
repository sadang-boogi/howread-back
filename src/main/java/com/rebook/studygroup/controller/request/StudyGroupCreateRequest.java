package com.rebook.studygroup.controller.request;

import com.rebook.studygroup.service.command.StudyGroupCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudyGroupCreateRequest {

    @NotBlank(message = "스터디그룹 이름을 입력해주세요.")
    private String name;

    @NotNull(message = "최대 멤버 수를 입력해주세요.")
    @Min(value = 2, message = "최대 멤버 수는 최소 2명 이상이어야 합니다.")
    private Integer maxMembers;

    @Builder
    public StudyGroupCreateRequest(String name, Integer maxMembers) {
        this.name = name;
        this.maxMembers = maxMembers;
    }

    public StudyGroupCommand toCommand() {
        return StudyGroupCommand.builder()
                .name(name)
                .maxMembers(maxMembers)
                .build();
    }
}
