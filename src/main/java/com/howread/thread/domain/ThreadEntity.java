package com.howread.thread.domain;

import com.howread.common.domain.BaseEntity;
import com.howread.user.domain.UserEntity;
import jakarta.persistence.*;


@Entity
@Table(name = "thread")
public class ThreadEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: JSON 핸들링 처리 추가
    @Column(name = "content", nullable = false, columnDefinition = "json")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_id")
    private ThreadEntity root;
}
