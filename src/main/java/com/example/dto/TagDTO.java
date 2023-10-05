package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO {

    private Integer id;
    private String name;

    public TagDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
