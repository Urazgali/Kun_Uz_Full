package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer>,
        PagingAndSortingRepository<EmailHistoryEntity,Integer> {

    long countByEmailAndCreatedDateAfter(String email, LocalDateTime localDateTime);

    List<EmailHistoryEntity> findAllByEmail(String email);

    List<EmailHistoryEntity> findAllByCreatedDateBetween(LocalDateTime fromDate,LocalDateTime toDate);

}
