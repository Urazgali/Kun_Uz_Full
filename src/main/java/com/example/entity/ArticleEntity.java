package com.example.entity;

import com.example.entity.superr.BaseStringEntity;
import com.example.enums.ArticleStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity extends BaseStringEntity {


    @Column(name = "title", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "shared_count")
    private Integer sharedCount = 0;

    @Column(name = "image_id")
    private String image_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private AttachEntity image;

    @Column(name = "region_id")
    private Integer regionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;

    @Column(name = "category_id")
    private Integer categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "moderator_id")
    private Integer moderatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", insertable = false, updatable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Integer publisherId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
    private ProfileEntity publisher;

    @Enumerated(value = EnumType.STRING)
    private ArticleStatus status;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @OneToMany(mappedBy = "article")
    private List<ArticleTypesEntity> articleTypes;

    @OneToMany(mappedBy = "article")
    private List<ArticleTagsEntity> articleTags;

    @Column(name = "like_count")
    private Integer likeCount=0;

    @Column(name = "dislike_count")
    private Integer dislikeCount=0;
}
