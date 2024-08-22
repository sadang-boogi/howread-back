package com.howread.hashtag.domain;

import com.howread.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLRestriction;

@SQLRestriction(value = "is_deleted = false")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hashtag")
@Entity
public class HashtagEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("해시태그 이름")
    @Column(name = "name", nullable = false)
    private String name;

    public HashtagEntity(
            final Long id,
            final String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static HashtagEntity of(final String name) {
        return new HashtagEntity(
                null,
                name);
    }

    public void changeName(String name) {
        this.name = name;
    }
}
