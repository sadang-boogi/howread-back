package com.rebook.studygroup.domain;

import com.rebook.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@SQLRestriction(value = "is_deleted = false")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "study_group_member")
@Entity
public class StudyGroupMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroupEntity studyGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StudyGroupApplicationStatus status;

    @Builder
    public StudyGroupMemberEntity(StudyGroupEntity studyGroup, UserEntity user, StudyGroupApplicationStatus status) {
        this.studyGroup = studyGroup;
        this.user = user;
        this.status = status;
    }

    public void accept() {
        this.status = StudyGroupApplicationStatus.ACCEPTED;
    }

    public void reject() {
        this.status = StudyGroupApplicationStatus.REJECTED;
    }
}
