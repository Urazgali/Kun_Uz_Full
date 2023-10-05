package com.example.dto;

import com.example.enums.ProfileRole;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@NoArgsConstructor
public class JwtDTO {

    private Integer id;
    private ProfileRole role;
    private String phone;
    public JwtDTO(Integer id, ProfileRole role) {
        this.id = id;
        this.role = role;
    }

    public JwtDTO( String phone,ProfileRole role) {
        this.phone = phone;
        this.role = role;
    }
}
