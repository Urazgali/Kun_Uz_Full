package com.example.controller;

import com.example.service.SmsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/sms/history")
public class SmsHistoryController {
    @Autowired
    private SmsHistoryService smsHistoryService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{phone}")
    public ResponseEntity<?> get(@PathVariable("phone") String phone) {
        return ResponseEntity.ok(smsHistoryService.get(phone));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/date")
    public ResponseEntity<?> getByDate(@RequestParam("date") LocalDate date) {
        return ResponseEntity.ok(smsHistoryService.getByDate(date));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/page")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(smsHistoryService.pagination(page - 1, size));
    }
}
