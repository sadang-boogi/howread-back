package com.rebook.studygroup.service;

import com.rebook.common.exception.NotFoundException;
import com.rebook.studygroup.domain.StudyGroupEntity;
import com.rebook.studygroup.domain.StudyGroupMemberEntity;
import com.rebook.studygroup.repository.StudyGroupMemberRepository;
import com.rebook.studygroup.repository.StudyGroupRepository;
import com.rebook.studygroup.service.command.StudyGroupCreateCommand;
import com.rebook.studygroup.service.dto.StudyGroupDto;
import com.rebook.user.domain.UserEntity;
import com.rebook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // userId로 사용자 조회
        UserEntity leader = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER_ID));

        // 스터디그룹 생성
        StudyGroupEntity savedStudyGroup = studyGroupRepository.save(StudyGroupEntity.builder()
                .name(studyGroupCreateCommand.getName())
                .description(studyGroupCreateCommand.getDescription())
                .maxMembers(studyGroupCreateCommand.getMaxMembers())
                .build());

        // 스터디그룹 리더 생성
        StudyGroupMemberEntity leaderMember = studyGroupMemberRepository.save(
                StudyGroupMemberEntity.builder()
                        .studyGroup(savedStudyGroup)
                        .user(leader)
                        .role(LEADER)
                        .build());

        // 스터디그룹에 리더 추가
        savedStudyGroup.getMembers().add(leaderMember);

        return StudyGroupDto.fromEntity(savedStudyGroup);
    }

    @Transactional(readOnly = true)
    public StudyGroupDto getStudyGroup(Long id) {
        StudyGroupEntity studyGroup = studyGroupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_STUDY_GROUP_ID));

        return StudyGroupDto.fromEntity(studyGroup);
    }
}
