package com.example.repository;

import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer>,
        PagingAndSortingRepository<CategoryEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible=false where id=:id")
    int deletedById(@Param("id") Integer id);
    List<CategoryEntity> findAllByVisibleTrueOrderByOrderNumber();
}
