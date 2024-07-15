package com.rebook.studygroup.controller;

import com.rebook.auth.annotation.Authenticated;
import com.rebook.auth.annotation.LoginRequired;
import com.rebook.common.domain.PageInfo;
import com.rebook.common.schema.PageResponse;
import com.rebook.studygroup.controller.request.StudyGroupCreateRequest;
import com.rebook.studygroup.controller.response.StudyGroupApplicationResponse;
import com.rebook.studygroup.controller.response.StudyGroupResponse;
import com.rebook.studygroup.service.StudyGroupService;
import com.rebook.studygroup.service.command.StudyGroupApplicationCommand;
import com.rebook.studygroup.service.dto.StudyGroupApplicationDto;
import com.rebook.studygroup.service.dto.StudyGroupDto;
import com.rebook.user.service.dto.AuthClaims;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
            @RequestBody @Valid final StudyGroupCreateRequest request,
            @Parameter(hidden = true) @Authenticated AuthClaims leader
    ) {

        StudyGroupResponse studyGroupResponse = StudyGroupResponse.from(studyGroupService.create(request.toCommand(), leader.getUserId()));

        return ResponseEntity.created(URI.create("/api/v1/studygroups/" + studyGroupResponse.getId())).body(studyGroupResponse);
    }

    @GetMapping
    public ResponseEntity<PageResponse<StudyGroupResponse>> getStudyGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Slice<StudyGroupDto> studyGroups = studyGroupService.getStudyGroups(pageable);

        List<StudyGroupResponse> items = studyGroups.getContent()
                .stream()
                .map(StudyGroupResponse::from)
                .toList();

        PageInfo pageInfo = new PageInfo(studyGroups.getNumber(), studyGroups.getSize(), studyGroups.hasNext());

        PageResponse<StudyGroupResponse> response = new PageResponse<>(items, pageInfo);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{studyGroupId}")
    public ResponseEntity<StudyGroupResponse> getStudyGroup(@PathVariable Long studyGroupId) {
        StudyGroupResponse studyGroup = StudyGroupResponse.from(studyGroupService.getStudyGroup(studyGroupId));
        return ResponseEntity.ok().body(studyGroup);
    }

    @PostMapping("/{studyGroupId}/applications")
    public ResponseEntity<StudyGroupApplicationResponse> applyToStudyGroup(
            @PathVariable Long studyGroupId,
            @Parameter(hidden = true) @Authenticated final AuthClaims user
    ) {
        StudyGroupApplicationCommand command = StudyGroupApplicationCommand.builder()
                .studyGroupId(studyGroupId)
                .userId(user.getUserId())
                .build();

        StudyGroupApplicationDto applicationDto = studyGroupService.applyToStudyGroup(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(StudyGroupApplicationResponse.from(applicationDto));
    }

    @PatchMapping("/applications/{applicationId}/accept")
    public ResponseEntity<Void> acceptStudyGroupApplication(
            @PathVariable Long applicationId,
            @Parameter(hidden = true) @Authenticated final AuthClaims leader
    ) {
        studyGroupService.acceptStudyGroupApplication(leader.getUserId(), applicationId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/applications/{applicationId}/reject")
    public ResponseEntity<Void> rejectStudyGroupApplication(
            @PathVariable Long applicationId,
            @Parameter(hidden = true) @Authenticated final AuthClaims leader
    ) {
        studyGroupService.rejectStudyGroupApplication(leader.getUserId(), applicationId);
        return ResponseEntity.ok().build();
    }
}
