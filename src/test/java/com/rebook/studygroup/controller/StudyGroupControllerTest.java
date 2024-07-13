package com.rebook.studygroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebook.auth.interceptor.LoginCheckInterceptor;
import com.rebook.studygroup.controller.request.StudyGroupCreateRequest;
import com.rebook.studygroup.service.StudyGroupService;
import com.rebook.studygroup.service.dto.StudyGroupDto;
import com.rebook.user.service.dto.AuthClaims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(StudyGroupController.class)
public class StudyGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudyGroupService studyGroupService;

    @MockBean
    private LoginCheckInterceptor loginCheckInterceptor;

    @Autowired
    private ObjectMapper objectMapper;

    private RequestPostProcessor loggedInUser() {
        return request -> {
            request.setAttribute("authClaims", new AuthClaims(1L));
            return request;
        };
    }

    @DisplayName("스터디그룹을 만들 때 이름을 적지 않으면 BAD_REQUEST 예외가 발생한다.")
    @Test
    void createStudyGroupWithoutName() throws Exception {
        // given
        StudyGroupCreateRequest badRequest = StudyGroupCreateRequest.builder()
                .name(null)
                .maxMembers(5)
                .build();

        when(loginCheckInterceptor.preHandle(any(), any(), any())).thenReturn(true);

        // when, then
        mockMvc.perform(post("/api/v1/studygroups")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(badRequest))
                        .with(loggedInUser()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("스터디그룹 이름을 입력해주세요."));
    }

    @DisplayName("스터디그룹을 만들 때 최대 멤버 수가 2명 미만이면 BAD_REQUEST 예외가 발생한다.")
    @Test
    void createStudyGroupInvalidMaxMembers() throws Exception {
        // given
        StudyGroupCreateRequest badRequest = StudyGroupCreateRequest.builder()
                .name("테스트 스터디그룹")
                .maxMembers(1)
                .build();

        when(loginCheckInterceptor.preHandle(any(), any(), any())).thenReturn(true);

        // when, then
        mockMvc.perform(post("/api/v1/studygroups")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(badRequest))
                        .with(loggedInUser()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("최대 멤버 수는 최소 2명 이상이어야 합니다."));
    }

    @DisplayName("스터디그룹 목록을 조회할 때 정상적으로 조회된다.")
    @Test
    void getStudyGroups() throws Exception {
        // given
        StudyGroupDto studyGroupDto = StudyGroupDto.builder()
                .id(1L)
                .name("Test Study Group")
                .maxMembers(5)
                .leaderId(1L)
                .build();

        SliceImpl<StudyGroupDto> studyGroups = new SliceImpl<>(List.of(studyGroupDto), PageRequest.of(0, 10), false);

        when(studyGroupService.getStudyGroups(any(Pageable.class)))
                .thenReturn(studyGroups);

        // when, then
        mockMvc.perform(get("/api/v1/studygroups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @DisplayName("스터디그룹을 조회할 때 정상적으로 조회된다.")
    @Test
    void getStudyGroup() throws Exception {
        // given
        StudyGroupDto studyGroupDto = StudyGroupDto.builder()
                .id(1L)
                .name("Test Study Group")
                .maxMembers(5)
                .leaderId(1L)
                .build();

        when(studyGroupService.getStudyGroup(1L))
                .thenReturn(studyGroupDto);

        // when, then
        mockMvc.perform(get("/api/v1/studygroups/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}