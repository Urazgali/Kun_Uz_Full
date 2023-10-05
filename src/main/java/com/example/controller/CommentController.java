package com.example.controller;

import com.example.dto.CommentDTO;
import com.example.dto.CommentFilterDTO;
import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.service.CommentService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERTOR','PUBLISHER','USER')")
    @PostMapping(value = "")
    public ResponseEntity<?> create(@Valid @RequestBody CommentDTO dto) {
        return ResponseEntity.ok(commentService.create(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERTOR','PUBLISHER','USER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String commentId,
                                    @Valid @RequestBody CommentDTO dto) {
        return ResponseEntity.ok(commentService.update(commentId, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERTOR','PUBLISHER','USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String commentId) {
        commentService.delete(commentId);
        return ResponseEntity.ok("Deleted !!!");
    }

    @GetMapping(value = "/getList/{id}")
    public ResponseEntity<?> getListByArticleId(@PathVariable("id") String articleId) {
        return ResponseEntity.ok(commentService.getListByArticleId(articleId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/page")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(commentService.getPagination(page - 1, size));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/filter")
    public ResponseEntity<?> filter(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    @RequestBody CommentFilterDTO filterDTO) {
        return ResponseEntity.ok(commentService.filter(page - 1, size, filterDTO));
    }

    @GetMapping(value = "/replyList/{id}")
    public ResponseEntity<?> getReplyCommentList(@PathVariable("id") String commentId) {
        return ResponseEntity.ok(commentService.replyList(commentId));
    }
}
