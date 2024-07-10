package com.rebook.study.repository;

import com.rebook.study.domain.StudyGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGroupRepository extends JpaRepository<StudyGroupEntity, Long> {
}
