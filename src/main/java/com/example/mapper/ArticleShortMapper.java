package com.example.mapper;

import com.example.entity.AttachEntity;

import java.time.LocalDateTime;

public interface ArticleShortMapper {
    String getId();

    String getDescription();

    String getTitle();

    String getImage();

    LocalDateTime getPublishedDate();

}
