package com.example.dto;

import com.example.enums.ArticleStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ArticleFilterDTO {

    private String id;
    private String title;
    private Integer regionId;
    private Integer categoryId;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private LocalDate publishedDateFrom;
    private LocalDate publishedDateTo;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;
}
