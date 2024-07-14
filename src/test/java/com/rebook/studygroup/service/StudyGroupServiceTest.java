package com.rebook.studygroup.service;

import com.rebook.common.exception.ExceptionCode;
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
import com.rebook.user.domain.Role;
import com.rebook.user.domain.UserEntity;
import com.rebook.user.repository.UserRepository;
import com.rebook.user.util.SocialType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyGroupServiceTest {

    @Mock
    private StudyGroupRepository studyGroupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudyGroupMemberRepository studyGroupMemberRepository;

    @InjectMocks
    private StudyGroupService studyGroupService;

    private UserEntity leader;
    private UserEntity member;
    private StudyGroupCreateCommand studyGroupCreateCommand;
    private StudyGroupEntity studyGroup;

    @BeforeEach
    void setUp() {
        leader = new UserEntity(1L, "리더", "leader@email.com", Role.USER, SocialType.GOOGLE, "1234");
        member = new UserEntity(2L, "멤버", "member@email.com", Role.USER, SocialType.GOOGLE, "5678");

        studyGroupCreateCommand = StudyGroupCreateCommand.builder()
                .name("스터디그룹")
                .maxMembers(5)
                .leaderId(leader.getId())
                .build();

        studyGroup = StudyGroupEntity.of(
                studyGroupCreateCommand.getName(),
                studyGroupCreateCommand.getMaxMembers(),
                leader
        );
    }

    @DisplayName("스터디그룹을 생성할 수 있다.")
    @Test
    void createStudyGroup() {
        // given
        when(userRepository.findById(leader.getId()))
                .thenReturn(Optional.of(leader));

        when(studyGroupRepository.save(any(StudyGroupEntity.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        // when
        StudyGroupDto studyGroupDto = studyGroupService.create(studyGroupCreateCommand, leader.getId());

        // then
        assertThat(studyGroupDto.getName()).isEqualTo(studyGroupCreateCommand.getName());
        assertThat(studyGroupDto.getMaxMembers()).isEqualTo(studyGroupCreateCommand.getMaxMembers());
        assertThat(studyGroupDto.getLeaderId()).isEqualTo(studyGroupCreateCommand.getLeaderId());
    }

    @DisplayName("유저가 스터디그룹에 참여 신청할 수 있다.")
    @Test
    void applyToStudyGroup() {
        // given
        StudyGroupApplicationCommand applicationCommand = StudyGroupApplicationCommand.builder()
                .studyGroupId(studyGroup.getId())
                .userId(member.getId())
                .build();

        when(userRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(studyGroupRepository.findById(studyGroup.getId())).thenReturn(Optional.of(studyGroup));
        when(studyGroupMemberRepository.save(any(StudyGroupMemberEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        // when
        StudyGroupApplicationDto applicationDto = studyGroupService.applyToStudyGroup(applicationCommand);

        // then
        assertThat(applicationDto.getStudyGroupId()).isEqualTo(studyGroup.getId());
        assertThat(applicationDto.getUserId()).isEqualTo(member.getId());
        assertThat(applicationDto.getStatus()).isEqualTo(StudyGroupApplicationStatus.PENDING);
    }

    @DisplayName("리더가 스터디그룹 참여 신청을 수락할 수 있다.")
    @Test
    void acceptStudyGroupApplication() {
        // given
        StudyGroupMemberEntity application = StudyGroupMemberEntity.builder()
                .studyGroup(studyGroup)
                .user(member)
                .status(StudyGroupApplicationStatus.PENDING)
                .build();

        when(studyGroupMemberRepository.findById(application.getId())).thenReturn(Optional.of(application));

        // when
        studyGroupService.acceptStudyGroupApplication(leader.getId(), application.getId());

        // then
        assertThat(application.getStatus()).isEqualTo(StudyGroupApplicationStatus.ACCEPTED);
        verify(studyGroupMemberRepository, times(1)).save(application);
    }

    @DisplayName("리더가 스터디그룹 참여 신청을 거절할 수 있다.")
    @Test
    void rejectStudyGroupApplication() {
        // given
        StudyGroupMemberEntity application = StudyGroupMemberEntity.builder()
                .studyGroup(studyGroup)
                .user(member)
                .status(StudyGroupApplicationStatus.PENDING)
                .build();

        when(studyGroupMemberRepository.findById(application.getId())).thenReturn(Optional.of(application));

        // when
        studyGroupService.rejectStudyGroupApplication(leader.getId(), application.getId());

        // then
        assertThat(application.getStatus()).isEqualTo(StudyGroupApplicationStatus.REJECTED);
        verify(studyGroupMemberRepository, times(1)).save(application);
    }

    @DisplayName("리더가 아닌 유저가 스터디그룹 참여 신청을 수락하려고 하면 예외가 발생한다.")
    @Test
    void acceptStudyGroupApplicationByNonLeader() {
        // given
        StudyGroupMemberEntity application = StudyGroupMemberEntity.builder()
                .studyGroup(studyGroup)
                .user(member)
                .status(StudyGroupApplicationStatus.PENDING)
                .build();

        when(studyGroupMemberRepository.findById(application.getId())).thenReturn(Optional.of(application));

        // when, then
        assertThatThrownBy(() -> studyGroupService.acceptStudyGroupApplication(member.getId(), application.getId()))
                .isInstanceOf(UnAuthorizedException.class)
                .hasMessageContaining(ExceptionCode.UN_AUTHORIZED.getMessage());
    }
}