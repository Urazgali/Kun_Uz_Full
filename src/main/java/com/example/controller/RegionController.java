package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.RegionDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody RegionDTO dto) {
        return ResponseEntity.ok(regionService.create( dto));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                   @Valid @RequestBody RegionDTO dto) {
        regionService.update(id, dto);
        return ResponseEntity.ok("Region update !!!");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        regionService.delete(id);
        return ResponseEntity.ok("Region deleted !!!");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping("/lan")
    public ResponseEntity<?> getByLan(@RequestParam("lan") Language lan) {
        return ResponseEntity.ok(regionService.getByLan(lan));
    }

}
