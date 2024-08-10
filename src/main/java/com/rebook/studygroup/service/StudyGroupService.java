package com.rebook.studygroup.service;

import static com.rebook.common.exception.ExceptionCode.*;
import static com.rebook.studygroup.domain.StudyGroupMemberRole.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

		return StudyGroupDto.fromEntity(savedStudyGroup);
	}

	@Transactional(readOnly = true)
	public List<StudyGroupDto> getStudyGroups() {
		return studyGroupRepository.findAll().stream()
			.map(StudyGroupDto::fromEntity)
			.toList();
	}

	@Transactional(readOnly = true)
	public StudyGroupDto getStudyGroup(Long id) {
		StudyGroupEntity studyGroup = studyGroupRepository.findStudyGroupById(id)
			.orElseThrow(() -> new NotFoundException(NOT_FOUND_STUDY_GROUP_ID));

		return StudyGroupDto.fromEntity(studyGroup);
	}

	@Transactional(readOnly = true)
	public List<StudyGroupMemberDto> getStudyGroupMembers(Long studyGroupId) {
		List<StudyGroupMemberEntity> studyGroupMembers = studyGroupMemberRepository.findByStudyGroupId(studyGroupId)
			.orElseThrow(() -> new NotFoundException(NOT_FOUND_STUDY_GROUP_ID));

		return studyGroupMembers.stream()
			.map(StudyGroupMemberDto::fromEntity)
			.toList();
	}
}
