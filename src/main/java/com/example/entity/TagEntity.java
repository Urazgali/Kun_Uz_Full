package com.example.entity;

import com.example.entity.superr.BaseIdentityEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagEntity extends BaseIdentityEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
