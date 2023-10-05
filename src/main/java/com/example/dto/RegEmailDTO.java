package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegEmailDTO {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Field cannot be empty")
    @Size(min = 3, message = "Name should be at least 3 characters")
    private String name;
    @NotNull(message = "Surname is required")
    @NotBlank(message = "Field cannot be empty")
    @Size(min = 3, message = "Surname should be at least 3 characters")
    private String surname;
    @NotNull(message = "Email is required")
    @NotBlank(message = "Field cannot be empty")
    @Email(message = "Email format in valid")
    private String email;
    @NotNull(message = "Password is required")
    @NotBlank(message = "Field cannot be empty")
    @Size(min = 3, message = "Password should be at least 3 characters")
    private String password;

}
