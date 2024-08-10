package com.rebook.studygroup.repository;

import com.rebook.studygroup.domain.StudyGroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudyGroupMemberRepository extends JpaRepository<StudyGroupMemberEntity, Long> {

    @Query("""
            SELECT m FROM StudyGroupMemberEntity m
            JOIN FETCH m.user
            WHERE m.studyGroup.id = :studyGroupId
            """)
    Optional<List<StudyGroupMemberEntity>> findByStudyGroupId(@Param("studyGroupId") Long studyGroupId);
}
