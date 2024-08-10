package com.rebook.studygroup.controller.response;

import com.rebook.studygroup.domain.StudyGroupMemberRole;
import com.rebook.studygroup.service.dto.StudyGroupMemberDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudyGroupMemberResponse {

	private Long id;
	private String nickname;
	private StudyGroupMemberRole role;
	private String avatarUrl;

	public static StudyGroupMemberResponse from(StudyGroupMemberDto studyGroupMemberDto) {
		return new StudyGroupMemberResponse(
			studyGroupMemberDto.getId(),
			studyGroupMemberDto.getNickname(),
			studyGroupMemberDto.getRole(),
			studyGroupMemberDto.getAvatarUrl()
		);
	}

}
