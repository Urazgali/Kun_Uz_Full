package com.example.service;

import com.example.dto.TagDTO;
import com.example.entity.ArticleTagsEntity;
import com.example.entity.ArticleTypesEntity;
import com.example.entity.TagEntity;
import com.example.repository.ArticleTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTagsService {
    @Autowired
    private ArticleTagsRepository articleTagsRepository;

    public void create(String articleId, List<Integer> tagsList) {
        tagsList.forEach(tagId -> {
            save(articleId, tagId);
        });
    }

    public void save(String articleId, Integer tagId) {
        ArticleTagsEntity entity = new ArticleTagsEntity();
        entity.setArticleId(articleId);
        entity.setTagId(tagId);
        articleTagsRepository.save(entity);
    }

    public void merge(String articleId, List<Integer> newList) {
        List<Integer> oldList = articleTagsRepository.getOldAticleTypeList(articleId);
        for (Integer typeId : newList) {
            if (!oldList.contains(typeId)) {
                save(articleId, typeId);
            }
        }
        for (Integer typeId : oldList) {
            if (!newList.contains(typeId)) {
                articleTagsRepository.deleteByArticleIdAndAndArticleTypeId(articleId, typeId);
            }
        }
    }

    public List<TagDTO> get(String articleId) {
        List<ArticleTagsEntity> list = articleTagsRepository.findAllByArticleId(articleId);
        return list.stream().map(e -> {
            return new TagDTO(e.getTag().getId(), e.getTag().getName());
        }).toList();
    }
}
