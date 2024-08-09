package com.rebook.studygroup.repository;

import com.rebook.studygroup.domain.StudyGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudyGroupRepository extends JpaRepository<StudyGroupEntity, Long> {
    @Query("""
            SELECT sg FROM StudyGroupEntity sg
            JOIN FETCH sg.members
            WHERE sg.id = :id
            """)
    StudyGroupEntity findStudyGroupById(@Param("id") Long id);


}
