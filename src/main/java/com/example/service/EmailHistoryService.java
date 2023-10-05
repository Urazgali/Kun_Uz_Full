package com.example.service;

import com.example.dto.SendEmailDTO;
import com.example.entity.EmailHistoryEntity;
import com.example.exp.AppBadRequestException;
import com.example.repository.EmailHistoryRepository;
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
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public void save(String toAccount, String text) {
        long emailCount = emailHistoryRepository.countByEmailAndCreatedDateAfter(toAccount, LocalDateTime.now().minusMinutes(1));
        if (emailCount == 4) throw new AppBadRequestException("Please try again in 1 minute");

        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(toAccount);
        entity.setMessage(text);
        emailHistoryRepository.save(entity);
    }

    public List<SendEmailDTO> get(String email) {
        if (email == null) return null;
        List<EmailHistoryEntity> entityList = emailHistoryRepository.findAllByEmail(email);
        return entityList.stream().map(this::toDTO).toList();
    }

    public List<SendEmailDTO> getByDate(LocalDate date) {
        if (date == null) return null;
        List<EmailHistoryEntity> entityList = emailHistoryRepository
                .findAllByCreatedDateBetween(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
        return entityList.stream().map(this::toDTO).toList();
    }

    public PageImpl<SendEmailDTO> pagination(int page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmailHistoryEntity> entityPage = emailHistoryRepository.findAll(pageable);
        return new PageImpl<>(entityPage.getContent().stream().map(this::toDTO).toList(), pageable, entityPage.getTotalElements());
    }

    public SendEmailDTO toDTO(EmailHistoryEntity entity) {
        SendEmailDTO dto = new SendEmailDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
