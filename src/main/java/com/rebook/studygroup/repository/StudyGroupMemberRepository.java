package com.rebook.studygroup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rebook.studygroup.domain.StudyGroupMemberEntity;

public interface StudyGroupMemberRepository extends JpaRepository<StudyGroupMemberEntity, Long> {

	@Query("""
		SELECT m FROM StudyGroupMemberEntity m
		JOIN FETCH m.user
		WHERE m.studyGroup.id = :studyGroupId
		""")
	Optional<List<StudyGroupMemberEntity>> findByStudyGroupId(@Param("studyGroupId") Long studyGroupId);
}
