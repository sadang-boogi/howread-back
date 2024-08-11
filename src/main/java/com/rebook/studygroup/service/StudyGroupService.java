package com.rebook.studygroup.service;

import com.rebook.common.exception.NotFoundException;
import com.rebook.studygroup.domain.StudyGroupEntity;
import com.rebook.studygroup.domain.StudyGroupMemberEntity;
import com.rebook.studygroup.repository.StudyGroupMemberRepository;
import com.rebook.studygroup.repository.StudyGroupRepository;
import com.rebook.studygroup.service.command.StudyGroupCreateCommand;
import com.rebook.studygroup.service.dto.StudyGroupDto;
import com.rebook.studygroup.service.dto.StudyGroupMemberDto;
import com.rebook.user.domain.UserEntity;
import com.rebook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rebook.common.exception.ExceptionCode.NOT_FOUND_STUDY_GROUP_ID;
import static com.rebook.common.exception.ExceptionCode.NOT_FOUND_USER_ID;
import static com.rebook.studygroup.domain.StudyGroupMemberRole.LEADER;

@RequiredArgsConstructor
@Service
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final StudyGroupMemberRepository studyGroupMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public StudyGroupDto createStudyGroup(final StudyGroupCreateCommand studyGroupCreateCommand, final Long userId) {
        UserEntity leader = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER_ID));

        StudyGroupEntity savedStudyGroup = studyGroupRepository.save(StudyGroupEntity.builder()
                .name(studyGroupCreateCommand.getName())
                .description(studyGroupCreateCommand.getDescription())
                .maxMembers(studyGroupCreateCommand.getMaxMembers())
                .build());

        StudyGroupMemberEntity leaderMember = studyGroupMemberRepository.save(
                StudyGroupMemberEntity.builder()
                        .studyGroup(savedStudyGroup)
                        .user(leader)
                        .role(LEADER)
                        .build());

        savedStudyGroup.getMembers().add(leaderMember);

        return StudyGroupDto.from(savedStudyGroup);
    }

    @Transactional(readOnly = true)
    public List<StudyGroupDto> getStudyGroups(int page, int pageSize) {
        PageRequest pageable = PageRequest.of(page - 1, pageSize);
        Page<StudyGroupEntity> result = studyGroupRepository.findStudyGroupEntitiesBy(pageable);
        return result.getContent().stream()
                .map(StudyGroupDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudyGroupDto getStudyGroup(Long id) {
        StudyGroupEntity studyGroup = studyGroupRepository.findStudyGroupById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_STUDY_GROUP_ID));

        return StudyGroupDto.from(studyGroup);
    }

    @Transactional(readOnly = true)
    public List<StudyGroupMemberDto> getStudyGroupMembers(Long studyGroupId) {
        StudyGroupEntity studyGroup = studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_STUDY_GROUP_ID));

        return studyGroup.getMembers().stream()
                .map(StudyGroupMemberDto::from)
                .toList();
    }
}
