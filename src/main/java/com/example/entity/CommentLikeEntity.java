package com.example.entity;

import com.example.entity.superr.BaseEntity;
import com.example.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment_like")
public class CommentLikeEntity extends BaseEntity {

    @Column(name = "profile_id")
    private Integer profileId;

    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "comment_id")
    private String commentId;

    @ManyToOne
    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    private CommentEntity comment;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private LikeStatus status;
}
