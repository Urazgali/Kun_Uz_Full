package com.example.service;

import com.example.dto.TagDTO;
import com.example.entity.TagEntity;
import com.example.exp.AppBadRequestException;
import com.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public TagDTO create(TagDTO dto) {
        Optional<TagEntity> optional = tagRepository.findAllByName(dto.getName());
        if (optional.isPresent()) throw new AppBadRequestException("This tag already create");
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        tagRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public boolean delete(Integer tagId) {
        Optional<TagEntity> optional = tagRepository.findByIdAndVisibleTrue(tagId);
        if (optional.isEmpty()) throw new AppBadRequestException("This tag not");
        TagEntity entity = optional.get();
        entity.setVisible(false);
        tagRepository.save(entity);
        return true;
    }

    public TagDTO change(Integer tagId) {
        Optional<TagEntity> optional = tagRepository.findById(tagId);
        if (optional.isEmpty()) throw new AppBadRequestException("This tag not");
        TagEntity entity = optional.get();
        entity.setVisible(!entity.getVisible());
        tagRepository.save(entity);
        return new TagDTO(entity.getId(), entity.getName());
    }

    public List<TagDTO> get() {
        List<TagDTO> list = new LinkedList<>();
        tagRepository.findAll().forEach(e -> {
            list.add(new TagDTO(e.getId(), e.getName()));
        });
        return list;
    }
}
