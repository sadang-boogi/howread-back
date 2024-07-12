package com.rebook.studygroup.repository;

import com.rebook.studygroup.domain.StudyGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGroupRepository extends JpaRepository<StudyGroupEntity, Long> {

}
