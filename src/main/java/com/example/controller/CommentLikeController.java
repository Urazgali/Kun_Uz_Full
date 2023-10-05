package com.example.controller;

import com.example.dto.CommentLikeDTO;
import com.example.dto.JwtDTO;
import com.example.service.CommentLikeService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/commentLike/")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;


    @PostMapping(value = "/like/{commentId}")
    public ResponseEntity<?> like(@PathVariable("commentId") String commentId) {
        return ResponseEntity.ok(commentLikeService.like(commentId));
    }

    @PostMapping(value = "/dislike/{commentId}")
    public ResponseEntity<?> dislike(@PathVariable("commentId") String commentId) {
        return ResponseEntity.ok(commentLikeService.dislike(commentId));
    }

    @DeleteMapping(value = "/remove/{commentId}")
    public ResponseEntity<?> remove(@PathVariable("commentId") String commentId) {
        return ResponseEntity.ok(commentLikeService.remove(commentId));
    }
}
