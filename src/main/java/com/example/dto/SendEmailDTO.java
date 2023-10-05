package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendEmailDTO {
    private Integer id;
    private String email;
    private String message;
    private LocalDateTime createdDate;
}
