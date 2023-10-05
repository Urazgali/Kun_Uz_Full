package com.example.repository;

import com.example.entity.SavedArticleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity,Integer> {


    void deleteAllByArticleIdAndProfileId(String articleId,Integer profileId);
    List<SavedArticleEntity> findByProfileId(Integer id);
}
