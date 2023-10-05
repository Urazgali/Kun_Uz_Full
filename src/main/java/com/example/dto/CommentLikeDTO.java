package com.example.dto;

import com.example.enums.LikeStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentLikeDTO {

    private Integer id;
    private Integer profileId;
    private String commentId;
    private LocalDateTime createdDate;
    private LikeStatus status;
}
