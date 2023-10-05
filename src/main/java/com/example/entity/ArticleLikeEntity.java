package com.example.entity;

import com.example.entity.superr.BaseEntity;
import com.example.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "article_like")
public class ArticleLikeEntity extends BaseEntity {

    @Column(name = "profile_id")
    private Integer profileId;

    @ManyToOne
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;

    @Column(name = "article_id")
    private String articleId;

    @ManyToOne
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private ArticleEntity article;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private LikeStatus status;
}
