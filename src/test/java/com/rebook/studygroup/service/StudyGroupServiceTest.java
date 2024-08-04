package com.rebook.studygroup.service;

import com.rebook.common.exception.NotFoundException;
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

    private UserEntity leader;

    @BeforeEach
    void setUp() {
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
    void createStudyGroup() {
        // given
        String studyGroupName = "스터디그룹";
        String description = "스터디그룹 설명";
        int maxMemberCount = 10;

        // when
        StudyGroupDto studyGroup = studyGroupService.create(studyGroupName, description, maxMemberCount, leader.getId());

        // then
        assertThat(studyGroup).isNotNull();
        assertThat(studyGroup.getId()).isEqualTo(1L);
        assertThat(studyGroup.getName()).isEqualTo(studyGroupName);
        assertThat(studyGroup.getDescription()).isEqualTo(description);
        assertThat(studyGroup.getMaxMemberCount()).isEqualTo(maxMemberCount);
        assertThat(studyGroup.getCurrentMemberCount()).isEqualTo(1);
    }

    @DisplayName("리더 없이 스터디그룹을 생성하면 예외가 발생한다.")
    @Test
    void createStudyGroupWithOutLeader() {
        // given
        String studyGroupName = "스터디그룹";
        String description = "스터디그룹 설명";
        int maxMemberCount = 10;

        // when, then
        assertThatThrownBy(() -> studyGroupService.create(studyGroupName, description, maxMemberCount, 100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("유저가 존재하지 않습니다.");
    }
}