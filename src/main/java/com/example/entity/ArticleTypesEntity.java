package com.example.entity;

import com.example.entity.superr.BaseStringEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "article_types")
public class ArticleTypesEntity extends BaseStringEntity {

    @Column(name = "article_id")
    private String articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private ArticleEntity article;

    @Column(name = "article_type_id")
    private Integer articleTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_type_id",insertable = false,updatable = false)
    private ArticleTypeEntity articleType;
}
