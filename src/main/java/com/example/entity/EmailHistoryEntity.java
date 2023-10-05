package com.example.entity;

import com.example.entity.superr.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "email_history")
public class EmailHistoryEntity extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String message;
    @Column(name = "email", nullable = false)
    private String email;


}
