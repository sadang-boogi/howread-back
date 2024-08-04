package com.rebook.studygroup.repository;

import com.rebook.studygroup.domain.StudyGroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGroupMemberRepository extends JpaRepository<StudyGroupMemberEntity, Long> {
}
