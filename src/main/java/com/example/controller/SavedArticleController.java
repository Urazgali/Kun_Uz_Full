package com.example.controller;

import com.example.service.SavedArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/savedArticle")
public class SavedArticleController {
    @Autowired
    private SavedArticleService savedArticleService;

    @PostMapping("/{id}")
    private ResponseEntity<?> create(@PathVariable("id") String articleId) {
        savedArticleService.create(articleId);
        return ResponseEntity.ok("Save !!!");
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") String articleId) {
        savedArticleService.delete(articleId);
        return ResponseEntity.ok("Deleted !!!");
    }
    @GetMapping("")
    private ResponseEntity<?> get() {
        return ResponseEntity.ok(savedArticleService.get());
    }
}
