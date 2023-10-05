package com.example.entity.superr;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class BaseStringEntity extends SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}
