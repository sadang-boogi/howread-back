package com.rebook.studygroup.service;

import com.rebook.studygroup.domain.StudyGroupEntity;
import com.rebook.studygroup.repository.StudyGroupRepository;
import com.rebook.studygroup.service.command.StudyGroupCommand;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyGroupServiceTest {

    @Mock
    private StudyGroupRepository studyGroupRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StudyGroupService studyGroupService;

    private UserEntity leader;
    private StudyGroupCommand studyGroupCommand;

    @BeforeEach
    void setUp() {
        leader = new UserEntity(1L, "유저", "test@email.com", Role.USER, SocialType.GOOGLE, "1234");

        studyGroupCommand = StudyGroupCommand.builder()
                .name("스터디그룹")
                .maxMembers(5)
                .leaderId(leader.getId())
                .build();
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
        StudyGroupDto studyGroupDto = studyGroupService.create(studyGroupCommand, leader.getId());

        // then
        assertThat(studyGroupDto.getName()).isEqualTo(studyGroupCommand.getName());
        assertThat(studyGroupDto.getMaxMembers()).isEqualTo(studyGroupCommand.getMaxMembers());
        assertThat(studyGroupDto.getLeaderId()).isEqualTo(studyGroupCommand.getLeaderId());
    }
}