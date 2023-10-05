package com.example.entity.superr;

import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class ParentLangEntity extends BaseSequenceEntity {

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "prt_id")
    private Integer prtId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prt_id", insertable = false, updatable = false)
    private ProfileEntity profile;

}
