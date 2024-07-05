package com.rebook.study.domain;

import com.rebook.common.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLRestriction;

@SQLRestriction(value = "is_deleted = false")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "study_group")
@Entity
public class StudyGroupEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Min(value = 2, message = "최소 참여 인원은 2명 이상이어야 합니다.")
    @Column(name = "max_members", nullable = false)
    private int maxMembers;

    @Comment("스터디 공개 여부")
    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    @Column(name = "password")
    private String password;
}
