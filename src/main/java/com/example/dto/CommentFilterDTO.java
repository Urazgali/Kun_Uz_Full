package com.example.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentFilterDTO {
    private String id;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private Integer profileId;
    private String articleId;

}
