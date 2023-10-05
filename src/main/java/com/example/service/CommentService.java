package com.example.service;

import com.example.dto.*;
import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.CommentRepository;
import com.example.repository.CustomRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CustomRepository customRepository;

    public CommentDTO create(CommentDTO dto) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getProfile().getId();
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        entity.setReplyId(dto.getReplyId());
        entity.setProfileId(profileId);
        commentRepository.save(entity); // save
        dto.setId(entity.getId());  // response
        return dto;
    }

    public CommentDTO update(String commentId, CommentDTO dto) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getProfile().getId();
        CommentEntity entity = getById(commentId);
        if (!entity.getProfileId().equals(profileId)) throw new AppBadRequestException("Comment not update");
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        entity.setUpdateDate(LocalDateTime.now());
        commentRepository.save(entity);
        // response
        dto.setId(entity.getId());
        return dto;
    }

    public void delete( String commentId) {
        CommentEntity entity = getById(commentId); // check comment
        ProfileEntity profile=SpringSecurityUtil.getCurrentUser().getProfile();
        Integer profileId;
        if (profile.getRole().equals(ProfileRole.ROLE_ADMIN)) profileId = entity.getProfileId();
        else profileId = profile.getId();
        int effectRow = commentRepository.delete(commentId, profileId); // update visible
        if (effectRow != 1) throw new AppBadRequestException("Comment not deleted");
    }


    public List<CommentDTO> getListByArticleId(String articleId) {
        List<CommentEntity> commentList = commentRepository.getByArticleIdAndVisibleTrue(articleId);
        return commentList.stream().map(this::getShortInfo).toList();
    }

    public PageImpl<CommentDTO> getPagination(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentEntity> entityPage = commentRepository.findAllByVisibleTrue(pageable);
        return new PageImpl<>(entityPage.map(this::getPageInfo).getContent(), pageable, entityPage.getTotalElements());
    }

    public PageImpl<CommentDTO> filter(Integer page, Integer size, CommentFilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(page, size);
        FilterResultDTO<CommentEntity> resultDTO = customRepository.filterComment(filterDTO, page, size);
        List<CommentDTO> dtoList = resultDTO.getList().stream().map(this::getFilterInfo).toList();
        return new PageImpl<>(dtoList, pageable, resultDTO.getTotalCount());
    }

    public List<CommentDTO> replyList(String commentId) {
        List<CommentEntity> commentList = commentRepository.getByReplyIdAndVisibleTrue(commentId);
        return commentList.stream().map(this::getShortInfo).toList();
    }

    public CommentDTO getShortInfo(CommentEntity entity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(entity.getId());
        commentDTO.setContent(entity.getContent());
        commentDTO.setCreatedDate(entity.getCreatedDate());
        // create profile dto
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(entity.getProfile().getId());
        profileDTO.setName(entity.getProfile().getName());
        profileDTO.setSurname(entity.getProfile().getSurname());
        // set profile
        commentDTO.setProfile(profileDTO);
        return commentDTO;
    }

    public CommentDTO getPageInfo(CommentEntity entity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(entity.getId());
        commentDTO.setCreatedDate(entity.getCreatedDate());
        commentDTO.setUpdateDate(entity.getUpdateDate());
        commentDTO.setContent(entity.getContent());
        commentDTO.setReplyId(entity.getReplyId());
        // create profile
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(entity.getProfileId());
        profileDTO.setName(entity.getProfile().getName());
        profileDTO.setSurname(entity.getProfile().getSurname());
        // set
        commentDTO.setProfile(profileDTO);
        // create article
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(entity.getArticleId());
        articleDTO.setTitle(entity.getArticle().getTitle());
        // set
        commentDTO.setArticle(articleDTO);
        return commentDTO;
    }

    public CommentDTO getFilterInfo(CommentEntity entity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(entity.getId());
        commentDTO.setCreatedDate(entity.getCreatedDate());
        commentDTO.setUpdateDate(entity.getUpdateDate());
        commentDTO.setProfileId(entity.getProfileId());
        commentDTO.setContent(entity.getContent());
        commentDTO.setArticleId(entity.getArticleId());
        commentDTO.setReplyId(entity.getReplyId());
        commentDTO.setVisible(entity.getVisible());
        return commentDTO;
    }

    public CommentEntity getById(String commentId) {
        return commentRepository.getByIdAndVisibleTrue(commentId).
                orElseThrow(() -> new ItemNotFoundException("Comment not found"));
    }


}
