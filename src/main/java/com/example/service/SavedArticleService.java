package com.example.service;

import com.example.dto.ArticleDTO;
import com.example.dto.AttachDTO;
import com.example.entity.SavedArticleEntity;
import com.example.repository.SavedArticleRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SavedArticleService {
    @Autowired
    private SavedArticleRepository savedArticleRepository;
    @Autowired
    private AttachService attachService;

    public void create(String articleId) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getProfile().getId();
        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticleId(articleId);
        entity.setProfileId(profileId);
        savedArticleRepository.save(entity);
    }

    public void delete(String articleId) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getProfile().getId();
        savedArticleRepository.deleteAllByArticleIdAndProfileId(articleId, profileId);
    }

    public List<ArticleDTO> get() {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getProfile().getId();
        List<SavedArticleEntity> entities = savedArticleRepository.findByProfileId(profileId);
        List<ArticleDTO> list = new LinkedList<>();
        entities.forEach(e -> {
            ArticleDTO dto = new ArticleDTO();
            dto.setId(e.getArticleId());
            dto.setTitle(e.getArticle().getTitle());
            dto.setDescription(e.getArticle().getDescription());
            dto.setAttachDTO(attachService.getDTO(e.getArticle().getImage_id()));
            list.add(dto);
        });
        return list;
    }
}
