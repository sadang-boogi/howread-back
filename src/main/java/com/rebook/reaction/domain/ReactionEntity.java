package com.rebook.reaction.domain;

import com.rebook.common.domain.BaseEntity;
import com.rebook.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@SQLRestriction("is_deleted = false")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reaction" , uniqueConstraints = {@UniqueConstraint(name = "uk_reaction_user_id_reaction_type_target_type_target_id", columnNames = {"user_id","reaction_type","target_type", "target_id"} ) })
public class ReactionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type", nullable = false, columnDefinition = "varchar(255)")
    private ReactionType reactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, columnDefinition = "varchar(255)")
    private TargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(name = "is_on", nullable = false)
    private boolean isOn;

    public void turnOn() {
        this.isOn = true;
    }
    public void turnOff() {
        this.isOn = false;
    }
}
