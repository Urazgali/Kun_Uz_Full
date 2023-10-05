package com.example.controller;

import com.example.dto.*;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = {"/registration"})           //   email
    public ResponseEntity<ApiResponseDTO> registrationEmail(@Valid @RequestBody RegEmailDTO dto) {
        return ResponseEntity.ok(authService.registrationEmail(dto));
    }

    @PostMapping(value = {"/registration/"})            //   phone
    public ResponseEntity<ApiResponseDTO> registrationPhone(@Valid @RequestBody RegPhoneDTO dto) {
        return ResponseEntity.ok(authService.registrationPhone(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(authService.login(authDTO));
    }

    @GetMapping(value = {"/verification/email/{jwt}"})    // email
    public ResponseEntity<ApiResponseDTO> verificationEmail(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }

    @GetMapping(value = {"/verification/phone"})    // phone
    public ResponseEntity<ApiResponseDTO> verificationPhone(@Valid @RequestBody PhoneVerificationCode dto) {
        return ResponseEntity.ok(authService.phoneVerification(dto));
    }
}
