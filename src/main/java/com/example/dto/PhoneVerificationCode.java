package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class PhoneVerificationCode {
    @NotNull(message = "Phone Required")
    @NotBlank(message = "Field cannot be empty")
    private String phone;
    @NotNull(message = "Message Required")
    @NotBlank(message = "Field cannot be empty")
    @Size(min = 5, max = 5, message = "5 character")
    private String message;
}
