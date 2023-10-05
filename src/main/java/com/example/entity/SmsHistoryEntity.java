package com.example.entity;

import com.example.entity.superr.BaseEntity;
import com.example.enums.SmsStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sms_history")
public class SmsHistoryEntity extends BaseEntity {
    @Column(name = "phone")
    private String phone;
    @Column(name = "message")
    private String message;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private SmsStatus status;


}
