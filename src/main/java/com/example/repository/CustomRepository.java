package com.example.repository;

import com.example.dto.ArticleFilterDTO;
import com.example.dto.CommentFilterDTO;
import com.example.dto.FilterResultDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomRepository {
    @Autowired
    private EntityManager entityManager;

    public List<ProfileEntity> filterProfile(ProfileFilterDTO filterDTO) {

        StringBuilder selectQueryBuilder = new StringBuilder("select s from ProfileEntity as s where 1=1");
        StringBuilder whereQuery = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (filterDTO.getName() != null) {
            whereQuery.append(" and s.name =:name");
            params.put("name", filterDTO.getName());
        }
        if (filterDTO.getSurname() != null) {
            whereQuery.append(" and s.surname =:surname");
            params.put("surname", filterDTO.getSurname());
        }
        if (filterDTO.getPhone() != null) {
            whereQuery.append(" and s.phone =:phone");
            params.put("phone", filterDTO.getPhone());
        }
        if (filterDTO.getRole() != null) {
            whereQuery.append(" and s.role =:role");
            params.put("role", filterDTO.getRole().toString());
        }

        if (filterDTO.getCreatedDateFrom() != null) {
            whereQuery.append(" and s.createdDate >=:fromDate");
            params.put("fromDate", LocalDateTime.of(filterDTO.getCreatedDateFrom(), LocalTime.MIN));
        }
        if (filterDTO.getCreatedDateTo() != null) {
            whereQuery.append(" and s.createdDate <=:ToDate");
            params.put("ToDate", LocalDateTime.of(filterDTO.getCreatedDateTo(), LocalTime.MAX));
        }
        Query selectQuery = entityManager.createQuery(selectQueryBuilder.append(whereQuery).toString());
        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
        }

        return selectQuery.getResultList();
    }

    public FilterResultDTO<CommentEntity> filterComment(CommentFilterDTO filterDTO,Integer page,Integer size) {
        StringBuilder selectQueryBuilder = new StringBuilder("select s from CommentEntity as s where 1=1");
        StringBuilder countQueryBuilder = new StringBuilder("select count(s) from CommentEntity as s where 1=1");
        StringBuilder whereQuery = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (filterDTO.getId() != null) {
            whereQuery.append(" and s.id =:id");
            params.put("id", filterDTO.getId());
        }
        if (filterDTO.getProfileId() != null) {
            whereQuery.append(" and s.profileId =:profileId");
            params.put("profileId", filterDTO.getProfileId());
        }
        if (filterDTO.getArticleId() != null) {
            whereQuery.append(" and s.articleId =:articleId");
            params.put("articleId", filterDTO.getArticleId());
        }
        if (filterDTO.getCreatedDateFrom() != null) {
            whereQuery.append(" and s.createdDate >=:createdDateFrom");
            params.put("createdDateFrom", LocalDateTime.of(filterDTO.getCreatedDateFrom(), LocalTime.MIN));
        }
        if (filterDTO.getCreatedDateTo() != null) {
            whereQuery.append(" and s.createdDate <=:createdDateTo");
            params.put("createdDateTo", LocalDateTime.of(filterDTO.getCreatedDateTo(), LocalTime.MAX));
        }
        whereQuery.append(" and visible=true");

        Query selectQuery = entityManager.createQuery(selectQueryBuilder.append(whereQuery).toString());
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);

        Query countQuery = entityManager.createQuery(countQueryBuilder.append(whereQuery).toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<CommentEntity> entityList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();
        return new FilterResultDTO<>(entityList, totalCount);
    }
    public FilterResultDTO<ArticleEntity> filterArticle(ArticleFilterDTO filterDTO,Integer page,Integer size){
        StringBuilder selectQueryBuilder = new StringBuilder("select s from ArticleEntity as s where 1=1");
        StringBuilder countQueryBuilder = new StringBuilder("select count(s) from ArticleEntity as s where 1=1");
        StringBuilder whereQuery = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (filterDTO.getId() != null) {
            whereQuery.append(" and s.id =:id");
            params.put("id", filterDTO.getId());
        }
        if (filterDTO.getTitle() != null) {
            whereQuery.append(" and s.title like :title");
            params.put("title", "%"+filterDTO.getTitle()+"%");
        }
        if (filterDTO.getRegionId() != null) {
            whereQuery.append(" and s.regionId =:regionId");
            params.put("regionId", filterDTO.getRegionId());
        }
        if (filterDTO.getCategoryId() != null) {
            whereQuery.append(" and s.categoryId =:categoryId");
            params.put("categoryId", filterDTO.getCategoryId());
        }
        if (filterDTO.getCreatedDateFrom() != null) {
            whereQuery.append(" and s.createdDate >=:createdDateFrom");
            params.put("createdDateFrom", LocalDateTime.of(filterDTO.getCreatedDateFrom(), LocalTime.MIN));
        }
        if (filterDTO.getCreatedDateTo() != null) {
            whereQuery.append(" and s.createdDate <=:createdDateTo");
            params.put("createdDateTo", LocalDateTime.of(filterDTO.getCreatedDateTo(), LocalTime.MAX));
        }
        if (filterDTO.getPublishedDateFrom() != null) {
            whereQuery.append(" and s.publishedDate >=:publishedDateFrom");
            params.put("publishedDateFrom", LocalDateTime.of(filterDTO.getCreatedDateFrom(), LocalTime.MIN));
        }
        if (filterDTO.getPublishedDateTo() != null) {
            whereQuery.append(" and s.publishedDate <=:publishedDateTo");
            params.put("publishedDateTo", LocalDateTime.of(filterDTO.getPublishedDateTo(), LocalTime.MAX));
        }
        if (filterDTO.getModeratorId() != null) {
            whereQuery.append(" and s.moderatorId =:moderatorId");
            params.put("moderatorId", filterDTO.getModeratorId());
        }
        if (filterDTO.getPublisherId() != null) {
            whereQuery.append(" and s.publisherId =:publisherId");
            params.put("publisherId", filterDTO.getPublisherId());
        }
        if (filterDTO.getStatus() != null) {
            whereQuery.append(" and s.status =:status");
            params.put("status", filterDTO.getStatus());
        }
        whereQuery.append(" and visible=true");
        Query selectQuery = entityManager.createQuery(selectQueryBuilder.append(whereQuery).toString());
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);

        Query countQuery = entityManager.createQuery(countQueryBuilder.append(whereQuery).toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<ArticleEntity> entityList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();
        return new FilterResultDTO<>(entityList, totalCount);
    }


}
