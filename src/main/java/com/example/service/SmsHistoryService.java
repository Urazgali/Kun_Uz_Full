package com.example.service;

import com.example.dto.SendEmailDTO;
import com.example.dto.SendPhoneDTO;
import com.example.entity.EmailHistoryEntity;
import com.example.entity.SmsHistoryEntity;
import com.example.enums.SmsStatus;
import com.example.exp.AppBadRequestException;
import com.example.repository.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    public void save(String phone, String message) {
        long smsCount = smsHistoryRepository.countByPhoneAndCreatedDateAfter(phone, LocalDateTime.now().minusMinutes(1));
        if (smsCount >= 4) {
            throw new AppBadRequestException("Please try again in 1 minute");
        }
        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setPhone(phone);
        entity.setMessage(message);
        entity.setStatus(SmsStatus.NEW);
        smsHistoryRepository.save(entity);
    }
    public List<SendPhoneDTO> get(String phone) {
        if (phone == null) return null;
        List<SmsHistoryEntity> entityList = smsHistoryRepository.findAllByPhone(phone);
        return entityList.stream().map(this::toDTO).toList();
    }
    public List<SendPhoneDTO> getByDate(LocalDate date) {
        if (date == null) return null;
        List<SmsHistoryEntity> entityList = smsHistoryRepository
                .findAllByCreatedDateBetween(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
        return entityList.stream().map(this::toDTO).toList();
    }
    public PageImpl<SendPhoneDTO> pagination(int page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SmsHistoryEntity> entityPage = smsHistoryRepository.findAll(pageable);
        return new PageImpl<>(entityPage.getContent().stream().map(this::toDTO).toList(), pageable, entityPage.getTotalElements());
    }
    public SendPhoneDTO toDTO(SmsHistoryEntity entity) {
        SendPhoneDTO dto = new SendPhoneDTO();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setPhone(entity.getPhone());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
