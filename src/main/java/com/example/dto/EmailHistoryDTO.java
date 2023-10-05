package com.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailHistoryDTO {
    private Integer id;
    private String message;
    private String email;
    private LocalDateTime createdDate;
}
