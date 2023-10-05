package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.CategoryDTO;
import com.example.dto.RegionDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.CategoryEntity;
import com.example.enums.Language;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.ArticleTypeRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        Integer prtId = SpringSecurityUtil.getCurrentUser().getProfile().getId();
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setPrtId(prtId);
        articleTypeRepository.save(entity); // save
        // response
        dto.setId(entity.getId());
        return dto;
    }

    public void update(Integer typeId, ArticleTypeDTO dto) {
        ArticleTypeEntity entity = getById(typeId);
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        articleTypeRepository.save(entity); // update
    }

    public void delete(Integer typeId) {
        getById(typeId); // check
        if (articleTypeRepository.deletedById(typeId) != 1) {
            throw new ItemNotFoundException("Type not deleted");
        }   // update visible
    }

    public PageImpl<ArticleTypeDTO> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderNumber").descending());
        Page<ArticleTypeEntity> pageList = articleTypeRepository.findAll(pageable);
        return new PageImpl<>(pageList.getContent().stream().map(this::toDTO).toList(), pageable, pageList.getTotalElements());
    }

    public List<ArticleTypeDTO> getByLan(Language lan) {
        List<ArticleTypeEntity> entities = articleTypeRepository.findAllByVisibleTrueOrderByOrderNumber();
        return entities.stream().map(entity -> getDTO(entity, lan)).toList();
    }

    public ArticleTypeDTO getDTO(ArticleTypeEntity entity, Language lan) {
        if (entity == null) return null;
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        switch (lan) {
            case en -> dto.setName(entity.getNameEn());
            case ru -> dto.setName(entity.getNameRu());
            default -> dto.setName(entity.getNameUz());
        }
        return dto;
    }

    public ArticleTypeEntity getById(Integer typeId) {
        if (typeId == null) throw new ItemNotFoundException("Article type not found");
        return articleTypeRepository.findById(typeId).
                orElseThrow(() -> new ItemNotFoundException("Article type not found."));
    }

    private ArticleTypeDTO toDTO(ArticleTypeEntity entity) {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
