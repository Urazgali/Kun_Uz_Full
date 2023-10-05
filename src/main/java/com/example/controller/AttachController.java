package com.example.controller;


import com.example.service.AttachService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attach")
public class AttachController {

    @Autowired
    private AttachService attachService;

    @PostMapping(value = "/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(attachService.save(file));
    }

    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] openById(@PathVariable("fileName") String fileName) {
        return attachService.loadImageById(fileName);
    }

    @GetMapping(value = "/open/{fileName}/general", produces = MediaType.ALL_VALUE)
    public byte[] openByIdGeneral(@PathVariable("fileName") String fileName) {
        return attachService.loadByIdGeneral(fileName);
    }

    @GetMapping(value = "/download/{fileName}")
    public ResponseEntity<?> download(@PathVariable("fileName") String fileName) {
        return attachService.download(fileName);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/pagination")
    public ResponseEntity<?> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(attachService.pagination(page - 1, size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{fileName}")
    public ResponseEntity<?> delete(@PathVariable("fileName") String fileName) {
        attachService.delete(fileName);
        return ResponseEntity.ok("Deleted !!!");
    }
}
