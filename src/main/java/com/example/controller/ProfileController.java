package com.example.controller;


import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @Valid @RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.update(id, dto));
    }
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','PUBLISHER','USER')")
    @PutMapping("/detail")
    public ResponseEntity<?> updateProfileDetail(@Valid @RequestBody ProfileDTO dto) {
        profileService.updateProfileDetail(dto);
        return ResponseEntity.ok("Update profile detail !!!");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(profileService.getAll(page - 1, size));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        profileService.delete(id);
        return ResponseEntity.ok("Deleted profile !!!");
    }
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','PUBLISHER','USER')")
    @PutMapping("/photo/{id}")
    public ResponseEntity<?> updatePhoto(@PathVariable("id") String photoId) {
        profileService.updatePhoto(photoId);
        return ResponseEntity.ok("Photo update !!!");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody ProfileFilterDTO filterDTO) {
        return ResponseEntity.ok(profileService.filter(filterDTO));
    }
}
