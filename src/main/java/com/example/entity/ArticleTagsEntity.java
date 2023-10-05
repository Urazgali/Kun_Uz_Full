package com.example.entity;

import com.example.entity.superr.BaseIdentityEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "article_tags")
public class ArticleTagsEntity extends BaseIdentityEntity {

    @Column(name = "article_id")
    private String articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private ArticleEntity article;

    @Column(name = "tag_id")
    private Integer tagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id",insertable = false,updatable = false)
    private TagEntity tag;


}
