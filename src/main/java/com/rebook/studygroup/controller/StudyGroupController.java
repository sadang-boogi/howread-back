package com.rebook.studygroup.controller;

import com.rebook.auth.annotation.Authenticated;
import com.rebook.auth.annotation.LoginRequired;
import com.rebook.studygroup.controller.request.StudyGroupCreateRequest;
import com.rebook.studygroup.controller.response.StudyGroupResponse;
import com.rebook.studygroup.service.StudyGroupService;
import com.rebook.studygroup.service.dto.StudyGroupMemberDto;
import com.rebook.user.service.dto.AuthClaims;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/studygroups")
@RestController
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    @LoginRequired
    @PostMapping
    public ResponseEntity<StudyGroupResponse> createStudyGroup(
            @RequestBody @Valid final StudyGroupCreateRequest studyGroupCreateRequest,
            @Parameter(hidden = true) @Authenticated AuthClaims claims
    ) {
        StudyGroupResponse studyGroupResponse = StudyGroupResponse.from(
                studyGroupService.createStudyGroup(studyGroupCreateRequest.toCommand(), claims.getUserId())
                , null
        );
        return ResponseEntity.created(URI.create("/api/v1/studygroups/" + studyGroupResponse.getId())).body(studyGroupResponse);
    }

    @GetMapping("/{studyGroupId}")
    public ResponseEntity<StudyGroupResponse> getStudyGroup(
            @PathVariable final Long studyGroupId
    ) {
        StudyGroupResponse studyGroupResponse = StudyGroupResponse.from(
                studyGroupService.getStudyGroup(studyGroupId),
                studyGroupService.getStudyGroupMembers(studyGroupId)
        );

        return ResponseEntity.ok()
                .body(studyGroupResponse);
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<StudyGroupMemberDto>> getStudyGroupMembers(
            @PathVariable Long id
    ) {
        List<StudyGroupMemberDto> members = studyGroupService.getStudyGroupMembers(id);

        return ResponseEntity.ok().body(members);
    }
}
