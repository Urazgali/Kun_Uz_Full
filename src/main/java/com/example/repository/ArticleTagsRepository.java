package com.example.repository;

import com.example.entity.ArticleTagsEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleTagsRepository extends CrudRepository<ArticleTagsEntity,Integer> {
    @Query("select a.tagId from ArticleTagsEntity as a where a.articleId=:articleId")
    List<Integer> getOldAticleTypeList(@Param("articleId") String articleId);
    @Transactional
    @Modifying
    @Query("delete from ArticleTagsEntity where articleId=:articleId and tagId=:tagId")
    void deleteByArticleIdAndAndArticleTypeId(@Param("articleId") String articleId,
                                              @Param("tagId") Integer tagId);

    List<ArticleTagsEntity> findAllByArticleId(String articleId);

}
