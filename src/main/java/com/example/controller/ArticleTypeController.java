package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.JwtDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.ArticleTypeService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articleType")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "")
    public ResponseEntity<?> create(@Valid @RequestBody ArticleTypeDTO dto) {
        return ResponseEntity.ok(articleTypeService.create(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @Valid @RequestBody ArticleTypeDTO dto) {
        articleTypeService.update(id, dto);
        return ResponseEntity.ok("Article type update !!!");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        articleTypeService.delete(id);
        return ResponseEntity.ok("Article type deleted !!!");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all/page")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(articleTypeService.getAll(page - 1, size));
    }

    @GetMapping(value = "/lan")
    public ResponseEntity<?> getByLan(@RequestParam("lan") Language lan) {
        return ResponseEntity.ok(articleTypeService.getByLan(lan));
    }


}
