package com.rebook.studygroup.service;

import com.rebook.common.exception.NotFoundException;
import com.rebook.common.exception.UnAuthorizedException;
import com.rebook.studygroup.domain.StudyGroupApplicationStatus;
import com.rebook.studygroup.domain.StudyGroupEntity;
import com.rebook.studygroup.domain.StudyGroupMemberEntity;
import com.rebook.studygroup.repository.StudyGroupMemberRepository;
import com.rebook.studygroup.repository.StudyGroupRepository;
import com.rebook.studygroup.service.command.StudyGroupApplicationCommand;
import com.rebook.studygroup.service.command.StudyGroupCreateCommand;
import com.rebook.studygroup.service.dto.StudyGroupApplicationDto;
import com.rebook.studygroup.service.dto.StudyGroupDto;
import com.rebook.user.domain.UserEntity;
import com.rebook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rebook.common.exception.ExceptionCode.*;

@RequiredArgsConstructor
@Service
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final StudyGroupMemberRepository studyGroupMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public StudyGroupDto create(final StudyGroupCreateCommand studyGroupCreateCommand,
                                final Long userId) {

        final UserEntity leader = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER_ID));

        final StudyGroupEntity savedStudyGroup = studyGroupRepository.save(StudyGroupEntity.of(
                studyGroupCreateCommand.getName(),
                studyGroupCreateCommand.getMaxMembers(),
                leader));

        return StudyGroupDto.fromEntity(savedStudyGroup);
    }

    @Transactional(readOnly = true)
    public Slice<StudyGroupDto> getStudyGroups(Pageable pageable) {
        Slice<StudyGroupEntity> studyGroupEntities = studyGroupRepository.findAllBy(pageable);
        List<StudyGroupDto> studyGroupDtos = studyGroupEntities.getContent()
                .stream()
                .map(StudyGroupDto::fromEntity)
                .toList();

        return new SliceImpl<>(studyGroupDtos, pageable, studyGroupEntities.hasNext());
    }

    @Transactional(readOnly = true)
    public StudyGroupDto getStudyGroup(final Long studyGroupId) {
        StudyGroupEntity studyGroup = studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_STUDYGROUP_ID));

        return StudyGroupDto.fromEntity(studyGroup);
    }

    @Transactional
    public StudyGroupApplicationDto applyToStudyGroup(StudyGroupApplicationCommand command) {
        UserEntity applyUser = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER_ID));

        StudyGroupEntity studyGroup = studyGroupRepository.findById(command.getStudyGroupId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_STUDYGROUP_ID));

        StudyGroupMemberEntity application = StudyGroupMemberEntity.builder()
                .studyGroup(studyGroup)
                .user(applyUser)
                .status(StudyGroupApplicationStatus.PENDING)
                .build();

        return StudyGroupApplicationDto.fromEntity(studyGroupMemberRepository.save(application));
    }

    @Transactional
    public void acceptStudyGroupApplication(Long leaderId, Long applicationId) {
        StudyGroupMemberEntity application = studyGroupMemberRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_APPLICATION_ID));

        if (isLeader(leaderId, application)) {
            throw new UnAuthorizedException(UN_AUTHORIZED);
        }

        application.accept();
        studyGroupMemberRepository.save(application);
    }

    @Transactional
    public void rejectStudyGroupApplication(Long leaderId, Long applicationId) {
        StudyGroupMemberEntity application = studyGroupMemberRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_APPLICATION_ID));

        if (isLeader(leaderId, application)) {
            throw new UnAuthorizedException(UN_AUTHORIZED);
        }

        application.reject();
        studyGroupMemberRepository.save(application);
    }

    private static boolean isLeader(Long leaderId, StudyGroupMemberEntity application) {
        return !application.getStudyGroup().getLeader().getId().equals(leaderId);
    }
}
