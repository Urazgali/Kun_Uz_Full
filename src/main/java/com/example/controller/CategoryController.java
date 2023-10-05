package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.JwtDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.CategoryService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @Valid @RequestBody CategoryDTO dto) {
        categoryService.update(id, dto);
        return ResponseEntity.ok("Category update !!!");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        categoryService.delete(id);
        return ResponseEntity.ok("Category deleted !!!");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/lan")
    public ResponseEntity<?> getByLan(@RequestParam("lan") Language lan) {
        return ResponseEntity.ok(categoryService.getByLan(lan));
    }
}
