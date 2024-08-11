package com.rebook.studygroup.controller;

import com.rebook.auth.annotation.Authenticated;
import com.rebook.auth.annotation.LoginRequired;
import com.rebook.common.schema.ListResponse;
import com.rebook.studygroup.controller.request.StudyGroupCreateRequest;
import com.rebook.studygroup.controller.response.StudyGroupResponse;
import com.rebook.studygroup.service.StudyGroupService;
import com.rebook.studygroup.service.dto.StudyGroupDto;
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
        );
        return ResponseEntity.created(URI.create("/api/v1/studygroups/" + studyGroupResponse.getId())).body(studyGroupResponse);
    }

    @GetMapping
    public ResponseEntity<ListResponse<StudyGroupResponse>> getStudyGroups(
            @RequestParam(required = false, defaultValue = "1") final int page,
            @RequestParam(required = false, defaultValue = "10") final int pageSize
    ) {
        List<StudyGroupDto> studyGroups = studyGroupService.getStudyGroups(page, pageSize);
        List<StudyGroupResponse> items = studyGroups.stream().map(
                StudyGroupResponse::from
        ).toList();
        return ResponseEntity.ok()
                .body(new ListResponse<>(items));
    }

    @GetMapping("/{studyGroupId}")
    public ResponseEntity<StudyGroupResponse> getStudyGroup(
            @PathVariable final Long studyGroupId
    ) {
        StudyGroupResponse studyGroupResponse = StudyGroupResponse.from(
                studyGroupService.getStudyGroup(studyGroupId)
        );

        return ResponseEntity.ok()
                .body(studyGroupResponse);
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<ListResponse<StudyGroupMemberDto>> getStudyGroupMembers(
            @PathVariable Long id
    ) {
        List<StudyGroupMemberDto> members = studyGroupService.getStudyGroupMembers(id);
        ListResponse<StudyGroupMemberDto> response = new ListResponse<>(members);

        return ResponseEntity.ok().body(response);
    }
}
