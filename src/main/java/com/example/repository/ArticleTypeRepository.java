package com.example.repository;

import com.example.entity.ArticleTypeEntity;
import com.example.entity.RegionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer>,
        PagingAndSortingRepository<ArticleTypeEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update ArticleTypeEntity set visible=false where id=:id")
    int deletedById(@Param("id") Integer id);

    List<ArticleTypeEntity> findAllByVisibleTrueOrderByOrderNumber();
}
