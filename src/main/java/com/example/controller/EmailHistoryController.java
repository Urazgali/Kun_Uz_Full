package com.example.controller;

import com.example.enums.ProfileRole;
import com.example.service.EmailHistoryService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/email/history")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService emailHistoryService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{email}")
    public ResponseEntity<?> get(@PathVariable("email") String email) {
        return ResponseEntity.ok(emailHistoryService.get(email));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/date")
    public ResponseEntity<?> getByDate(@RequestParam("date") LocalDate date) {
        return ResponseEntity.ok(emailHistoryService.getByDate(date));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/page")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(emailHistoryService.pagination(page - 1, size));
    }
}
