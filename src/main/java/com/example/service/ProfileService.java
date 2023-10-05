package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.entity.ProfileEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.AttachRepository;
import com.example.repository.CustomRepository;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import com.example.util.PhoneIsValidUtil;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CustomRepository customRepository;
    @Autowired
    private AttachRepository attachRepository;


    public ProfileDTO create(ProfileDTO dto) {
        Integer prtId = SpringSecurityUtil.getCurrentUser().getProfile().getId();
        if (getByPhone(dto.getPhone()) != null) {         // check phone
            throw new ItemNotFoundException("Email already exists.");
        }
        if (getByEmail(dto.getEmail()) != null) {         // check email
            throw new ItemNotFoundException("Phone already exists.");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setRole(dto.getRole());
        entity.setPrtId(prtId);
        entity.setStatus(dto.getStatus());
        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ProfileDTO update(Integer profileId, ProfileDTO dto) {
        ProfileEntity entity = getById(profileId);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setVisible(dto.getVisible());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(dto.getStatus());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        profileRepository.save(entity); //  update
        return dto;
    }

    public void updateProfileDetail(ProfileDTO dto) {
        Integer profileId=SpringSecurityUtil.getCurrentUser().getProfile().getId();
        if (dto.getName() == null) throw new AppBadRequestException("Name required");
        if (dto.getSurname() == null) throw new AppBadRequestException("Surname required"); //  check ?
        profileRepository.updateDetail(profileId, dto.getName(), dto.getSurname());
    }

    public PageImpl<ProfileDTO> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProfileEntity> entityPage = profileRepository.findAll(pageable);
        return new PageImpl<>(entityPage.getContent().stream().map(this::toDTO).toList(), pageable, entityPage.getTotalElements());
    }

    public void delete(Integer profileId) {
        getById(profileId); // check
        profileRepository.deletedById(profileId); // update visible
    }

    public void updatePhoto(String imageId) {
        Integer profileId=SpringSecurityUtil.getCurrentUser().getProfile().getId();
        if (attachRepository.findById(imageId).isPresent()) throw new ItemNotFoundException("Image not upload");
        profileRepository.updatePhoto(profileId, imageId); // update photo
    }

    public List<ProfileDTO> filter(ProfileFilterDTO filterDTO) {
        List<ProfileEntity> list = customRepository.filterProfile(filterDTO);
        return list.stream().map(entity -> {
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setName(entity.getName());
            profileDTO.setSurname(entity.getSurname());
            return profileDTO;
        }).toList();
    }

    public ProfileEntity getById(Integer id) {
        return profileRepository.findById(id).
                orElseThrow(() -> new ItemNotFoundException("Profile not found"));
    }

    public ProfileEntity getByPhone(String phone) {
        if (phone == null) return null;
        return profileRepository.findByPhone(phone).orElse(null);
    }

    public ProfileEntity getByEmail(String email) {
        if (email == null) return null;
        return profileRepository.findByEmail(email).orElse(null);
    }

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setVisible(entity.getVisible());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}


