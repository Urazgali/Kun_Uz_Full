package com.example.repository;

import com.example.entity.ArticleTypesEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleTypesRepository extends CrudRepository<ArticleTypesEntity, Integer> {

    @Query("select a.articleTypeId from ArticleTypesEntity as a where a.articleId=:articleId")
    List<Integer> getOldAticleTypeList(@Param("articleId") String articleId);

    @Transactional
    @Modifying
    @Query("delete from ArticleTypesEntity where articleId=:articleId and articleTypeId=:typeId")
    void deleteByArticleIdAndAndArticleTypeId(@Param("articleId") String articleId,
                                              @Param("typeId") Integer typeId);


}
