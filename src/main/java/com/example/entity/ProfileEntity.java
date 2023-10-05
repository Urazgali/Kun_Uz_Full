package com.example.entity;

import com.example.entity.superr.BaseIdentityEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseIdentityEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProfileStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ProfileRole role;

    @Column(name = "prt_id")
    private Integer prtId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prt_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "image_id")
    private String imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private AttachEntity image;

}
