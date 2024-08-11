package com.rebook.studygroup.repository;

import com.rebook.studygroup.domain.StudyGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rebook.studygroup.domain.StudyGroupEntity;

public interface StudyGroupRepository extends JpaRepository<StudyGroupEntity, Long> {

    Page<StudyGroupEntity> findStudyGroupEntitiesBy(PageRequest pageable);
    @Query("""
            SELECT sg FROM StudyGroupEntity sg
            JOIN FETCH sg.members m
            JOIN FETCH m.user
            WHERE sg.id = :id
            """)
    Optional<StudyGroupEntity> findStudyGroupById(@Param("id") Long id);
}
