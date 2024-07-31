package com.rebook.studygroup.domain;

import com.rebook.common.domain.BaseEntity;
import com.rebook.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLRestriction;

@DynamicInsert
@SQLRestriction(value = "is_deleted = false")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "study_group_member")
@Entity
public class StudyGroupMemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroupEntity studyGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING )
    @ColumnDefault("'MEMBER'")
    @Column(name = "grade", nullable = false, columnDefinition = "varchar(255)")
    private StudyGroupMemberRole role;
}