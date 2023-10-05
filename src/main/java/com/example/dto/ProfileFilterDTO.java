package com.example.dto;

import com.example.enums.ProfileRole;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileFilterDTO {

    private String name;
    private String surname;
    private String phone;
    private ProfileRole role;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
}
