package com.example.service;

import com.example.entity.ArticleLikeEntity;
import com.example.enums.LikeStatus;
import com.example.repository.ArticleLikeRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleLikeService {

    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    public boolean like(String articleId) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getProfile().getId();
        if (articleId == null) return false; // check
        ArticleLikeEntity entity = get(profileId, articleId);
        if (entity != null) remove(articleId);
        entity = new ArticleLikeEntity();
        entity.setProfileId(profileId);
        entity.setArticleId(articleId);
        entity.setStatus(LikeStatus.LIKE);
        articleLikeRepository.save(entity);
        return true;
    }

    public boolean dislike( String articleId) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getProfile().getId();
        if (articleId == null) return false; // check
        ArticleLikeEntity entity = get(profileId, articleId);
        if (entity != null) remove(articleId);
        entity = new ArticleLikeEntity();
        entity.setProfileId(profileId);
        entity.setArticleId(articleId);
        entity.setStatus(LikeStatus.DISLIKE);
        articleLikeRepository.save(entity);

        return true;
    }

    public boolean remove(String articleId) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getProfile().getId();
        if (profileId == null || articleId == null) return false;
        int effectRow = articleLikeRepository.remove(profileId, articleId);
        return effectRow == 1;

    }

    public ArticleLikeEntity get(Integer profileId, String articleId) {
        if (articleId == null) return null;
        Optional<ArticleLikeEntity> optional = articleLikeRepository.getByProfileIdAndArticleId(profileId, articleId);
        return optional.orElse(null);
    }
}
