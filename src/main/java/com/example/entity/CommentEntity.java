package com.example.entity;


import com.example.entity.superr.BaseStringEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity extends BaseStringEntity {

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "profile_id")
    private Integer profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "article_id")
    private String articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

    @Column(name = "reply_id")
    private String replyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id", insertable = false, updatable = false)
    private CommentEntity comment;


}
