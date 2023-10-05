package com.example.service;

import com.example.entity.ArticleTypesEntity;
import com.example.repository.ArticleTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypesService {
    @Autowired
    private ArticleTypesRepository articleTypesRepository;

    public void create(String articleId, List<Integer> typesList) {
        typesList.forEach(typeId -> {
            save(articleId, typeId);
        });
    }

    public void save(String articleId, Integer typeId) {
        ArticleTypesEntity entity = new ArticleTypesEntity();
        entity.setArticleId(articleId);
        entity.setArticleTypeId(typeId);
        articleTypesRepository.save(entity);
    }

    public void merge(String articleId, List<Integer> newList) {
        List<Integer> oldList = articleTypesRepository.getOldAticleTypeList(articleId);
        for (Integer typeId : newList) {
            if (!oldList.contains(typeId)) {
                save(articleId, typeId);
            }
        }
        for (Integer typeId : oldList){
            if (!newList.contains(typeId)){
                articleTypesRepository.deleteByArticleIdAndAndArticleTypeId(articleId,typeId);
            }
        }


    }

}
