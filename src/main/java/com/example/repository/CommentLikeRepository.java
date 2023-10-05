package com.example.repository;

import com.example.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, Integer> {
    @Transactional
    @Modifying
    @Query("delete from CommentLikeEntity where profileId=:profileId and commentId=:commentId")
    int remove(@Param("profileId") Integer profileId,
               @Param("commentId") String commentId);

    Optional<CommentLikeEntity> getByProfileIdAndCommentId(Integer profileId,String commentId);
}
