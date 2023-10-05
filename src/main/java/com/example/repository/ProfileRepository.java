package com.example.repository;

import com.example.entity.ProfileEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>,
        PagingAndSortingRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByPhone(String phone);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible=false where id=:id")
    int deletedById(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set name=:name,surname=:surname where id=:id")
    int updateDetail(@Param("id") Integer id, @Param("name") String name, @Param("surname") String surname);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set imageId=:imageId where id=:id")
    int updatePhoto(@Param("id") Integer id, @Param("imageId") String imageId);
}
