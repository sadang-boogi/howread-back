package com.rebook.studygroup.repository;

import com.rebook.studygroup.domain.StudyGroupEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudyGroupRepository extends JpaRepository<StudyGroupEntity, Long> {

    Slice<StudyGroupEntity> findAllBy(Pageable pageable);

    @Query("SELECT sg FROM StudyGroupEntity sg WHERE sg.id = :studyGroupId")
    Optional<StudyGroupEntity> findById(Long studyGroupId);
}
