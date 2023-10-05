package com.example.controller;

import com.example.dto.ArticleDTO;
import com.example.dto.ArticleFilterDTO;
import com.example.dto.JwtDTO;
import com.example.enums.ArticleStatus;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping(value = "")
    public ResponseEntity<?> create(@Valid @RequestBody ArticleDTO dto) {
        return ResponseEntity.ok(articleService.create(dto));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody ArticleDTO dto,
                                    @PathVariable("id") String id) {
        articleService.update(id, dto);
        return ResponseEntity.ok("Update article !!!");
    }
    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        articleService.delete(id);
        return ResponseEntity.ok("Article deleted !!!");
    }
    @PreAuthorize("hasRole('PUBLISHER')")
    @PutMapping(value = "/publish/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          @RequestParam("status") ArticleStatus status) {
        articleService.changeStatus(id, status);
        return ResponseEntity.ok("Update article status");
    }


    @GetMapping(value = "/lastFive")
    public ResponseEntity<?> getLastFiveByType(@RequestParam("type") Integer id) {
        return ResponseEntity.ok(articleService.getLastFiveByType(id));
    }

    @GetMapping(value = "/lastThree")
    public ResponseEntity<?> getLastThreeByType(@RequestParam("type") Integer id) {
        return ResponseEntity.ok(articleService.getLastThreeByType(id));
    }

    @GetMapping(value = "/lastEight")
    public ResponseEntity<?> getLastEight(@RequestBody List<String> list) {
        return ResponseEntity.ok(articleService.getLastEight(list));
    }

    @GetMapping(value = "/{id}/lan")
    public ResponseEntity<?> getByIdAndLang(@PathVariable("id") String articleId,
                                            @RequestParam("lan") Language lan) {
        return ResponseEntity.ok(articleService.getByIdAndLan(articleId, lan));
    }

    @GetMapping(value = "/lastFour/{id}/type")
    public ResponseEntity<?> getLastFourByType(@PathVariable("id") String articleId,
                                               @RequestParam("type") Integer typeId) {
        return ResponseEntity.ok(articleService.getLastFourByType(articleId, typeId));
    }

    @GetMapping(value = "/mostRead")
    public ResponseEntity<?> getMostReadList() {
        return ResponseEntity.ok(articleService.getMostReadList());
    }

    @GetMapping(value = "/tagName")
    public ResponseEntity<?> getLastByTagNameList(@RequestParam("tag") Integer tagId) {
        return ResponseEntity.ok(articleService.getByTagName(tagId));
    }

    @GetMapping(value = "/typeAndRegion")
    public ResponseEntity<?> getLastByTypeAndRegionList(@RequestParam("type") Integer typeId,
                                                        @RequestParam("region") Integer regionId) {
        return ResponseEntity.ok(articleService.getByTypeAndRegion(typeId, regionId));
    }

    @GetMapping(value = "/region/{id}")
    public ResponseEntity<?> getByRegionPagination(@PathVariable("id") Integer regionId,
                                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(articleService.getByRegionPagination(regionId, page - 1, size));
    }

    @GetMapping(value = "/lastFive/{id}")
    public ResponseEntity<?> getLastFiveByCategory(@PathVariable("id") Integer categoryId) {
        return ResponseEntity.ok(articleService.getLastFiveByCategory(categoryId));
    }

    @GetMapping(value = "/category/{id}")
    public ResponseEntity<?> getByCategoryPagination(@PathVariable("id") Integer categoryId,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(articleService.getLastFiveByCategory(categoryId, page - 1, size));
    }

    @PutMapping(value = "/view/{id}")
    public ResponseEntity<?> increaseViewCountById(@PathVariable("id") String articleId) {
        return ResponseEntity.ok(articleService.increaseViewCountById(articleId));
    }

    @PutMapping(value = "/shared/{id}")
    public ResponseEntity<?> increaseShareCountById(@PathVariable("id") String articleId) {
        return ResponseEntity.ok(articleService.increaseShareCountById(articleId));
    }
    @PreAuthorize("hasRole('PUBLISHER')")
    @GetMapping(value = "/filter")
    public ResponseEntity<?> filter(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    @RequestBody ArticleFilterDTO filterDTO) {
        return ResponseEntity.ok(articleService.filter(filterDTO, page - 1, size));
    }

}
