package com.rebook.reaction.domain;

import com.rebook.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@SQLRestriction(value = "is_deleted = false")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "reaction", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_reaction_user_id_reaction_type_target_type_target_id",
                columnNames = {"user_id", "reaction_type", "target_type", "target_id"}
        )
})
@Entity
public class ReactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type", nullable = false, columnDefinition = "varchar(255)")
    private ReactionType reactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, columnDefinition = "varchar(255)")
    private ReactionTargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    private UserEntity user;

    @Column(name = "is_on", nullable = false)
    private boolean isOn;

    public void on() {
        this.isOn = true;
    }
}
