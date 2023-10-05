package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.Data;;
import lombok.NoArgsConstructor;
;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO {

    private Integer id;
    @NotNull(message = "Order number required")
    private Integer orderNumber;
    private String name;
    @NotNull(message = "Name Uz required")
    private String nameUz;
    @NotNull(message = "Name Ru required")
    private String nameRu;
    @NotNull(message = "Name En required")
    private String nameEn;
    private Boolean visible;
    private LocalDateTime createdDate;
}
