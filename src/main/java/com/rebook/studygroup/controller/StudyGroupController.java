package com.rebook.studygroup.controller;

import com.rebook.auth.annotation.Authenticated;
import com.rebook.auth.annotation.LoginRequired;
import com.rebook.common.domain.PageInfo;
import com.rebook.common.schema.PageResponse;
import com.rebook.studygroup.controller.request.StudyGroupCreateRequest;
import com.rebook.studygroup.controller.response.StudyGroupResponse;
import com.rebook.studygroup.service.StudyGroupService;
import com.rebook.studygroup.service.dto.StudyGroupDto;
import com.rebook.user.service.dto.AuthClaims;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<PageResponse<StudyGroupResponse>> getStudyGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Slice<StudyGroupDto> studyGroups = studyGroupService.getStudyGroups(pageable);

        List<StudyGroupResponse> items = studyGroups.getContent()
                .stream()
                .map(StudyGroupResponse::fromDto)
                .collect(Collectors.toList());

        PageInfo pageInfo = new PageInfo(studyGroups.getNumber(), studyGroups.getSize(), studyGroups.hasNext());

        PageResponse<StudyGroupResponse> response = new PageResponse<>(items, pageInfo);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{studyGroupId}")
    public ResponseEntity<StudyGroupResponse> getStudyGroup(@PathVariable Long studyGroupId) {
        StudyGroupResponse studyGroup = StudyGroupResponse.fromDto(studyGroupService.getStudyGroup(studyGroupId));
        return ResponseEntity.ok().body(studyGroup);
    }

}
