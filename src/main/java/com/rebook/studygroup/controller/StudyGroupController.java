package com.rebook.studygroup.controller;

import com.rebook.auth.annotation.Authenticated;
import com.rebook.auth.annotation.LoginRequired;
import com.rebook.studygroup.controller.request.StudyGroupCreateRequest;
import com.rebook.studygroup.controller.response.StudyGroupResponse;
import com.rebook.studygroup.service.StudyGroupService;
import com.rebook.user.service.dto.AuthClaims;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/v1/studygroups")
@RestController
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    @LoginRequired
    @PostMapping
    public ResponseEntity<StudyGroupResponse> createStudyGroup(
            @RequestBody @Valid final StudyGroupCreateRequest request,
            @Parameter(hidden = true) @Authenticated AuthClaims leader
    ) {

        StudyGroupResponse studyGroupResponse = StudyGroupResponse.fromDto(studyGroupService.create(request.toCommand(), leader.getUserId()));

        return ResponseEntity.created(URI.create("/api/v1/studygroups/" + studyGroupResponse.getId())).body(studyGroupResponse);
    }
}
