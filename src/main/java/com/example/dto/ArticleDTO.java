package com.example.dto;

import com.example.enums.ArticleStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {

    private String id;
    @NotNull(message = "Title required")
    private String title;
    @NotNull(message = "Description required")
    private String description;
    @NotNull(message = "Content required")
    private String content;
    private String imageId;
    @NotNull(message = "Region required")
    private Integer regionId;
    @NotNull(message = "Category required")
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    @NotNull(message = "Article types required")
    private List<Integer> articleTypes;
    @NotNull(message = "Article tags required")
    private List<Integer> articleTags;
    private List<TagDTO> tagList;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
    private Integer viewCount;
    private Integer sharedCount;
    private AttachDTO attachDTO;
    private RegionDTO regionDTO;
    private CategoryDTO categoryDTO;
    private Integer likeCount;
    private Integer dislikeCount;
}
