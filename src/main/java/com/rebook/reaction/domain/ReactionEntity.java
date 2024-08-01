package com.rebook.reaction.domain;

import com.rebook.common.domain.BaseEntity;
import com.rebook.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLRestriction;

@Entity
@SQLRestriction("is_deleted = false")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reaction" , uniqueConstraints = {@UniqueConstraint(name = "uk_reaction_user_id_reaction_type_target_type_target_id", columnNames = {"user_id","reaction_type","target_type", "target_id"} ) })
public class ReactionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("리액션 유저")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;

    @Comment("리액션 유형")
    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type", columnDefinition = "varchar(255)")
    private ReactionType reactionType;

    @Comment("리액션 대상")
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", columnDefinition = "varchar(255)")
    private TargetType targetType;

    @Comment("리액션 대상 id")
    @Column(name = "target_id")
    private Long targetId;

    @Comment("활성화 여부")
    @Column(name = "is_on", nullable = false)
    private boolean isOn;

}
