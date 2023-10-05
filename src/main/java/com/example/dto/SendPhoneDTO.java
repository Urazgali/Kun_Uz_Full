package com.example.dto;

import com.example.enums.SmsStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SendPhoneDTO {
    private Integer id;
    private String message;
    private String phone;
    private SmsStatus status;
    private LocalDateTime createdDate;
}
