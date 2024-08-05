package com.rebook.studygroup.service;

import com.rebook.common.exception.NotFoundException;
import com.rebook.studygroup.repository.StudyGroupMemberRepository;
import com.rebook.studygroup.repository.StudyGroupRepository;
import com.rebook.studygroup.service.command.StudyGroupCreateCommand;
import com.rebook.studygroup.service.dto.StudyGroupDto;
import com.rebook.user.domain.Role;
import com.rebook.user.domain.UserEntity;
import com.rebook.user.repository.UserRepository;
import com.rebook.user.util.SocialType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class StudyGroupServiceTest {

    @Autowired
    private StudyGroupService studyGroupService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private StudyGroupMemberRepository studyGroupMemberRepository;

    private UserEntity leader;

    @BeforeEach
    void setUp() {
        studyGroupMemberRepository.deleteAllInBatch();
        studyGroupRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        leader = UserEntity.builder()
                .email("email.com")
                .nickname("닉네임")
                .role(Role.USER)
                .socialType(SocialType.GOOGLE)
                .socialId("소셜아이디")
                .build();

        userRepository.save(leader);
    }

    @DisplayName("스터디그룹을 생성하고, 생성된 스터디그룹의 ID를 반환한다.")
    @Test
    void createStudyGroupStudyGroup() {
        // given
        String studyGroupName = "스터디그룹";
        String description = "스터디그룹 설명";
        int maxMemberCount = 10;

        StudyGroupCreateCommand studyGroupCreateCommand = new StudyGroupCreateCommand(studyGroupName, description, maxMemberCount);

        // when
        StudyGroupDto studyGroup = studyGroupService.createStudyGroup(studyGroupCreateCommand, leader.getId());

        // then
        assertThat(studyGroup).isNotNull();
        assertThat(studyGroup.getId()).isNotNull();
        assertThat(studyGroup.getName()).isEqualTo(studyGroupName);
        assertThat(studyGroup.getDescription()).isEqualTo(description);
        assertThat(studyGroup.getMaxMembers()).isEqualTo(maxMemberCount);
        assertThat(studyGroup.getCurrentMembers()).isEqualTo(1);
    }

    @DisplayName("리더 없이 스터디그룹을 생성하면 예외가 발생한다.")
    @Test
    void createStudyGroupStudyGroupWithOutLeader() {
        // given
        String studyGroupName = "스터디그룹";
        String description = "스터디그룹 설명";
        int maxMemberCount = 10;

        StudyGroupCreateCommand studyGroupCreateCommand = new StudyGroupCreateCommand(studyGroupName, description, maxMemberCount);

        // when, then
        assertThatThrownBy(() -> studyGroupService.createStudyGroup(studyGroupCreateCommand, 100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("유저가 존재하지 않습니다.");
    }

    @DisplayName("스터디그룹을 조회한다.")
    @Test
    void getStudyGroup() {
        // given
        String studyGroupName = "스터디그룹";
        String description = "스터디그룹 설명";
        int maxMemberCount = 10;

        StudyGroupCreateCommand studyGroupCreateCommand = new StudyGroupCreateCommand(studyGroupName, description, maxMemberCount);
        StudyGroupDto studyGroup = studyGroupService.createStudyGroup(studyGroupCreateCommand, leader.getId());

        // when
        StudyGroupDto foundStudyGroup = studyGroupService.getStudyGroup(studyGroup.getId());

        // then
        assertThat(foundStudyGroup).isNotNull();
        assertThat(foundStudyGroup.getId()).isEqualTo(studyGroup.getId());
        assertThat(foundStudyGroup.getName()).isEqualTo(studyGroupName);
        assertThat(foundStudyGroup.getDescription()).isEqualTo(description);
        assertThat(foundStudyGroup.getMaxMembers()).isEqualTo(maxMemberCount);
        assertThat(foundStudyGroup.getCurrentMembers()).isEqualTo(1);
    }
}