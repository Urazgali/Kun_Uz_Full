package com.example.controller;

import com.example.dto.TagDTO;
import com.example.service.TagService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody TagDTO dto) {
        return ResponseEntity.ok(tagService.create(dto));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer tagId) {
        return ResponseEntity.ok(tagService.change(tagId));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer tagId) {
        return ResponseEntity.ok(tagService.delete(tagId));
    }
    @PreAuthorize("hasAnyRole('MODERATOR','PUBLISHER','ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(tagService.get());
    }

}
