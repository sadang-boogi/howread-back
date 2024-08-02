package com.rebook.studygroup.controller;

import com.rebook.studygroup.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StudyGroupController {
    private final StudyGroupService studyGroupService;
}
