package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.service.ArticleLikeService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articleLike")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;


    @PostMapping(value = "/like/{articleId}")
    public ResponseEntity<?> like(@PathVariable("articleId") String articleId) {
        return ResponseEntity.ok(articleLikeService.like(articleId));
    }

    @PostMapping(value = "/dislike/{articleId}")
    public ResponseEntity<?> dislike(@PathVariable("articleId") String articleId) {
        return ResponseEntity.ok(articleLikeService.dislike(articleId));
    }

    @DeleteMapping(value = "/remove/{articleId}")
    public ResponseEntity<?> remove(@PathVariable("articleId") String articleId) {
        return ResponseEntity.ok(articleLikeService.remove(articleId));
    }
}
