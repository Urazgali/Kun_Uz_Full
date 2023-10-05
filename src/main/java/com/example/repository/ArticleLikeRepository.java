package com.example.repository;

import com.example.entity.ArticleLikeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer>,
        PagingAndSortingRepository<ArticleLikeEntity, Integer> {

    Optional<ArticleLikeEntity> getByProfileIdAndArticleId(Integer profileId, String articleId);

    @Transactional
    @Modifying
    @Query("delete from ArticleLikeEntity where profileId=:profileId and articleId=:articleId")
    int remove(@Param("profileId") Integer profileId,
               @Param("articleId") String articleId);

}
