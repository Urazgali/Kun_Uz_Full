package com.example.repository;

import com.example.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<TagEntity, Integer> {

    Optional<TagEntity> findAllByName(String name);
    Optional<TagEntity> findByIdAndVisibleTrue(Integer tagId);
}
