package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "attach")
public class AttachEntity {

    @Id
    private String id;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "path")
    private String path;

    @Column(name = "size")
    private Long size;

    @Column(name = "extension")
    private String extension;

    @Column(name = "created_date")
    private LocalDateTime createdData = LocalDateTime.now();


}
