package com.example.service;

import com.example.entity.CommentLikeEntity;
import com.example.enums.LikeStatus;
import com.example.repository.CommentLikeRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeService {

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    public boolean like(String commentId) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getProfile().getId();
        if (commentId == null) return false; // check
        CommentLikeEntity entity = get(profileId, commentId);
        if (entity != null) remove(commentId);

        entity = new CommentLikeEntity();
        entity.setProfileId(profileId);
        entity.setCommentId(commentId);
        entity.setStatus(LikeStatus.LIKE);
        commentLikeRepository.save(entity);

        return true;
    }

    public boolean dislike(String commentId) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getProfile().getId();
        if (commentId == null) return false; // check
        CommentLikeEntity entity = get(profileId, commentId);
        if (entity != null) remove(commentId);

        entity = new CommentLikeEntity();
        entity.setProfileId(profileId);
        entity.setCommentId(commentId);
        entity.setStatus(LikeStatus.DISLIKE);
        commentLikeRepository.save(entity);

        return true;
    }

    public boolean remove(String commentId) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getProfile().getId();
        if (profileId == null || commentId == null) return false;
        int effectRow = commentLikeRepository.remove(profileId, commentId);
        return effectRow == 1;
    }

    public CommentLikeEntity get(Integer profileId, String commentId) {
        if (commentId == null) return null;
        Optional<CommentLikeEntity> optional = commentLikeRepository.getByProfileIdAndCommentId(profileId, commentId);
        return optional.orElse(null);
    }

}
