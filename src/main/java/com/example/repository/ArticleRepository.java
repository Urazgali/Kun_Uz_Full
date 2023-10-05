package com.example.repository;


import com.example.entity.ArticleEntity;
import com.example.entity.CategoryEntity;
import com.example.enums.ArticleStatus;
import com.example.mapper.ArticleShortMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String>,
        PagingAndSortingRepository<ArticleEntity, String> {
    Optional<ArticleEntity> findByIdAndVisibleTrue(String articleId);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible=false where id=:id")
    int deletedById(@Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set status=:status,publishedDate=:publishedDate,publisherId=:publisher where id=:id")
    int changeStatus(@Param("id") String id,
                     @Param("status") ArticleStatus status,
                     @Param("publishedDate") LocalDateTime publishedDate,
                     @Param("publisher") Integer publisherId);


    @Query(value = "from ArticleEntity  as a inner join a.articleTypes as at " +
            "where at.articleTypeId=:typeId and a.status=PUBLISHED and a.visible=true " +
            "order by a.publishedDate desc limit :lim")
    List<ArticleEntity> getListByType(@Param("typeId") Integer articleTypeId,
                                      @Param("lim") Integer limit);

    @Query(value = "select id,title,description,image_id as image, published_date as publishedDate " +
            "from article  where  status='PUBLISHED' " +
            "and visible=true and id not in (:list) " +
            "order by published_date desc limit 8", nativeQuery = true)
    List<ArticleShortMapper> getEightList(@Param("list") List<String> list);



    @Query("from ArticleEntity as a inner join a.articleTypes as at " +
            "where at.articleTypeId=:typeId and a.status=PUBLISHED and a.visible=true " +
            "and a.id<>:articleId order by a.publishedDate desc limit 4")
    List<ArticleEntity> getLastFourByType(@Param("typeId") Integer typeId,
                                          @Param("articleId") String articleId);



    @Query("from ArticleEntity as a inner join a.articleTags as t where t.tagId=:tagId and " +
            "a.visible=true and a.status=PUBLISHED order by a.publishedDate desc limit 4")
    List<ArticleEntity> getListByTag(@Param("tagId") Integer tagId);

    @Query("from ArticleEntity as a inner join a.articleTypes as ar where ar.articleTypeId=:typeId " +
            "and a.visible=true and a.regionId=:regionId and a.status=PUBLISHED " +
            "order by a.publishedDate desc limit 5")
    List<ArticleEntity> getByTypeAndRegion(@Param("typeId") Integer typeId,
                                           @Param("regionId") Integer regionId);

    Page<ArticleEntity> findAllByRegionIdAndStatusAndVisibleTrue(Pageable pageable,Integer regionId, ArticleStatus status);
    Page<ArticleEntity> findAllByCategoryIdAndStatusAndVisibleTrue(Pageable pageable,Integer categoryId,ArticleStatus status);

    List<ArticleEntity> findTop5ByStatusAndCategoryIdAndVisibleTrue(ArticleStatus status,Integer id);
    List<ArticleEntity> findTop4ByStatusAndVisibleTrueOrderByViewCountDesc(ArticleStatus status);

    Optional<ArticleEntity> getByIdAndStatusAndVisibleTrue(String id, ArticleStatus status);
    @Transactional
    @Modifying
    @Query("update ArticleEntity set viewCount=viewCount+1 where id=:articleId ")
    int increaseView(@Param("articleId") String articleId);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set sharedCount=sharedCount+1 where id=:articleId ")
    int increaseShared(@Param("articleId") String articleId);
}
