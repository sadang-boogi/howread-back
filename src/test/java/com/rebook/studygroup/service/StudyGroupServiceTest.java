package com.rebook.studygroup.service;

import com.rebook.common.exception.NotFoundException;
import com.rebook.studygroup.domain.StudyGroupEntity;
import com.rebook.studygroup.domain.StudyGroupMemberEntity;
import com.rebook.studygroup.repository.StudyGroupMemberRepository;
import com.rebook.studygroup.repository.StudyGroupRepository;
import com.rebook.studygroup.service.command.StudyGroupCreateCommand;
import com.rebook.studygroup.service.dto.StudyGroupDto;
import com.rebook.studygroup.service.dto.StudyGroupMemberDto;
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

import java.util.List;

import static com.rebook.studygroup.domain.StudyGroupMemberRole.LEADER;
import static com.rebook.studygroup.domain.StudyGroupMemberRole.MEMBER;
import static org.assertj.core.api.Assertions.*;

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
    private UserEntity member1;
    private UserEntity member2;

    @BeforeEach
    void setUp() {
        studyGroupMemberRepository.deleteAllInBatch();
        studyGroupRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        leader = UserEntity.builder()
                .email("email.com")
                .nickname("리더")
                .role(Role.USER)
                .socialType(SocialType.GOOGLE)
                .socialId("소셜아이디")
                .build();

        userRepository.save(leader);

        member1 = UserEntity.builder()
                .email("email1.com")
                .nickname("닉네임1")
                .role(Role.USER)
                .socialType(SocialType.GOOGLE)
                .socialId("소셜아이디1")
                .build();

        member2 = UserEntity.builder()
                .email("email2.com")
                .nickname("닉네임2")
                .role(Role.USER)
                .socialType(SocialType.GOOGLE)
                .socialId("소셜아이디2")
                .build();

        userRepository.saveAll(List.of(member1, member2));
    }

    @DisplayName("스터디그룹을 생성하고, 생성된 스터디그룹의 ID를 반환한다.")
    @Test
    void createStudyGroupStudyGroup() {
        // given
        String studyGroupName = "스터디그룹";
        String description = "스터디그룹 설명";
        int maxMemberCount = 10;

        StudyGroupCreateCommand studyGroupCreateCommand = createStudyGroupCommand(studyGroupName, description, maxMemberCount);

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
        StudyGroupCreateCommand studyGroupCreateCommand = createStudyGroupCommand("스터디그룹", "스터디그룹 설명", 10);

        // when, then
        assertThatThrownBy(() -> studyGroupService.createStudyGroup(studyGroupCreateCommand, 100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("유저가 존재하지 않습니다.");
    }

    @DisplayName("스터디그룹 목록을 조회한다.")
    @Test
    void getStudyGroups() {
        // given
        String studyGroupName1 = "스터디그룹1";
        String description1 = "스터디그룹 설명1";
        int maxMemberCount1 = 10;

        String studyGroupName2 = "스터디그룹2";
        String description2 = "스터디그룹 설명2";
        int maxMemberCount2 = 5;

        StudyGroupCreateCommand studyGroupCommand1 = createStudyGroupCommand(studyGroupName1, description1, maxMemberCount1);
        StudyGroupCreateCommand studyGroupCommand2 = createStudyGroupCommand(studyGroupName2, description2, maxMemberCount2);

        studyGroupService.createStudyGroup(studyGroupCommand1, leader.getId());
        studyGroupService.createStudyGroup(studyGroupCommand2, leader.getId());

        // when
        List<StudyGroupDto> studyGroups = studyGroupService.getStudyGroups(1, 10);

        // then
        assertThat(studyGroups).hasSize(2);
        assertThat(studyGroups)
                .extracting("name", "description")
                .containsExactlyInAnyOrder(
                        tuple(studyGroupName1, description1),
                        tuple(studyGroupName2, description2)
                );
    }

    @DisplayName("단일 스터디그룹을 조회한다.")
    @Test
    void getStudyGroup() {
        // given
        String studyGroupName = "스터디그룹";
        String description = "스터디그룹 설명";
        int maxMemberCount = 10;

        StudyGroupCreateCommand studyGroupCreateCommand = createStudyGroupCommand(studyGroupName, description, maxMemberCount);
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

    @DisplayName("존재하지 않는 스터디그룹을 조회하면 예외가 발생한다.")
    @Test
    void getStudyGroupWithNotExistStudyGroup() {
        // when, then
        assertThatThrownBy(() -> studyGroupService.getStudyGroup(100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("요청하신 스터디그룹은 존재하지 않습니다.");
    }

    @DisplayName("스터디그룹의 멤버를 조회한다.")
    @Test
    void getStudyGroupMembers() {
        // given
        String studyGroupName = "스터디그룹";
        StudyGroupCreateCommand studyGroupCreateCommand = createStudyGroupCommand(studyGroupName, "스터디그룹 설명", 10);
        StudyGroupDto studyGroup = studyGroupService.createStudyGroup(studyGroupCreateCommand, leader.getId());

        StudyGroupEntity savedStudyGroup = studyGroupRepository.findById(studyGroup.getId()).get();

        StudyGroupMemberEntity memberEntity1 = StudyGroupMemberEntity.builder()
                .studyGroup(studyGroupRepository.findById(studyGroup.getId()).get())
                .user(member1)
                .role(MEMBER)
                .build();
        studyGroupMemberRepository.save(memberEntity1);

        StudyGroupMemberEntity memberEntity2 = StudyGroupMemberEntity.builder()
                .studyGroup(studyGroupRepository.findById(studyGroup.getId()).get())
                .user(member2)
                .role(MEMBER)
                .build();
        studyGroupMemberRepository.save(memberEntity2);

        savedStudyGroup.getMembers().add(memberEntity1);
        savedStudyGroup.getMembers().add(memberEntity2);

        // when
        List<StudyGroupMemberDto> members = studyGroupService.getStudyGroupMembers(studyGroup.getId());

        // then
        assertThat(members).isNotEmpty();
        assertThat(members.size()).isEqualTo(3); // Leader + 2 members
        assertThat(members)
                .extracting("nickname")
                .containsExactlyInAnyOrder("리더", "닉네임1", "닉네임2");
        assertThat(members)
                .extracting("role")
                .contains(LEADER, MEMBER, MEMBER);
    }

    private static StudyGroupCreateCommand createStudyGroupCommand(String studyGroupName, String description, int maxMemberCount) {
        StudyGroupCreateCommand studyGroupCreateCommand = new StudyGroupCreateCommand(studyGroupName, description, maxMemberCount);
        return studyGroupCreateCommand;
    }

}
