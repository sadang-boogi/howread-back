package com.rebook.studygroup.service;

import com.rebook.common.exception.NotFoundException;
import com.rebook.studygroup.domain.StudyGroupEntity;
import com.rebook.studygroup.repository.StudyGroupRepository;
import com.rebook.studygroup.service.command.StudyGroupCommand;
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

import static com.rebook.common.exception.ExceptionCode.NOT_FOUND_STUDYGROUP_ID;
import static com.rebook.common.exception.ExceptionCode.NOT_FOUND_USER_ID;

@RequiredArgsConstructor
@Service
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final UserRepository userRepository;

    @Transactional
    public StudyGroupDto create(final StudyGroupCommand studyGroupCommand,
                                final Long userId) {

        final UserEntity leader = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER_ID));

        final StudyGroupEntity savedStudyGroup = studyGroupRepository.save(StudyGroupEntity.of(
                studyGroupCommand.getName(),
                studyGroupCommand.getMaxMembers(),
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
}
