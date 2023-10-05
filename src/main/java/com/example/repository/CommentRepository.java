package com.example.repository;

import com.example.dto.CommentDTO;
import com.example.entity.CommentEntity;
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

public interface CommentRepository extends CrudRepository<CommentEntity, String>,
        PagingAndSortingRepository<CommentEntity, String> {

    Optional<CommentEntity> getByIdAndVisibleTrue(String commentId);

    @Transactional
    @Modifying
    @Query("update CommentEntity set visible=false where id=:id and profileId=:profileId")
    int delete(@Param("id") String commentId,
               @Param("profileId") Integer profileId);

    List<CommentEntity> getByArticleIdAndVisibleTrue(String articleId);

    Page<CommentEntity> findAllByVisibleTrue(Pageable pageable);

    List<CommentEntity> getByReplyIdAndVisibleTrue(String commentId);
}
