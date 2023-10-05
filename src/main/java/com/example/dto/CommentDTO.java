package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {

    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private Integer profileId;
    private ProfileDTO profile;
    @NotNull(message = "Content required")
    private String content;
    private String articleId;
    private ArticleDTO article;
    private Boolean visible;
    private String replyId;


}
