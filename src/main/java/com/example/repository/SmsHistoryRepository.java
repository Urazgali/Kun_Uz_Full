package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import com.example.entity.SmsHistoryEntity;
import com.example.enums.SmsStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, Integer>,
        PagingAndSortingRepository<SmsHistoryEntity, Integer> {

    long countByPhoneAndCreatedDateAfter(String phone, LocalDateTime localDateTime);

    List<SmsHistoryEntity> findAllByPhone(String phone);

    List<SmsHistoryEntity> findAllByCreatedDateBetween(LocalDateTime of, LocalDateTime of1);

    Optional<SmsHistoryEntity> findTop1ByPhoneAndStatusOrderByCreatedDateDesc(String phone, SmsStatus status);
}
