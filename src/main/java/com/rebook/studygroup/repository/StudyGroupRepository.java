package com.rebook.studygroup.repository;

import com.rebook.studygroup.domain.StudyGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGroupRepository extends JpaRepository<StudyGroupEntity, Long> {

    Page<StudyGroupEntity> findStudyGroupEntitiesBy(PageRequest pageable);
}
