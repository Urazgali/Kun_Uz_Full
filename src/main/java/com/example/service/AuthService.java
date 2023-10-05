package com.example.service;

import com.example.dto.*;
import com.example.entity.ProfileEntity;
import com.example.entity.SmsHistoryEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.enums.SmsStatus;
import com.example.exp.AppBadRequestException;
import com.example.repository.ProfileRepository;
import com.example.repository.SmsHistoryRepository;
import com.example.util.JWTUtil;
import com.example.util.MD5Util;
import com.example.util.PhoneIsValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private SmsSenderService smsSenderService;
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;


    @Value("${server.url}")
    private String serverUrl;


    public ApiResponseDTO registrationEmail(RegEmailDTO dto) {
        ProfileEntity profile = profileService.getByEmail(dto.getEmail());
        if (profile != null) {
            if (profile.getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(profile);
            } else return new ApiResponseDTO(false, "Email already exists.");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(entity);
        mailSenderService.sendEmailVerification(entity);
        return new ApiResponseDTO(true, "The verification link was send to email.");
    }

    public ApiResponseDTO registrationPhone(RegPhoneDTO dto) {
        if (!PhoneIsValidUtil.checkPhone(dto.getPhone())) throw new AppBadRequestException("Phone in valid");
        ProfileEntity profile = profileService.getByPhone(dto.getPhone());
        if (profile != null) {
            if (profile.getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(profile);
            } else return new ApiResponseDTO(false, "Phone already exists.");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(entity);
        smsSenderService.sendPhoneVerification(entity.getPhone());
        return new ApiResponseDTO(true, "A verification code has been sent to your phone.");
    }

    public ApiResponseDTO emailVerification(String jwt) {
        JwtDTO jwtDTO = JWTUtil.decodeEmailJwt(jwt);
        ProfileEntity entity = profileService.getById(jwtDTO.getId());

        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadRequestException("Wrong status");
        }
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity); // update
        return new ApiResponseDTO(true, "Registration completed");
    }

    public ApiResponseDTO phoneVerification(PhoneVerificationCode dto) {
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findTop1ByPhoneAndStatusOrderByCreatedDateDesc(dto.getPhone(), SmsStatus.NEW);
        if (optional.isEmpty()) throw new AppBadRequestException("The profile information is incorrect");
        SmsHistoryEntity smsEntity = optional.get();
        if (smsEntity.getCreatedDate().isBefore(LocalDateTime.now().minusMinutes(1))) {
            return new ApiResponseDTO(false, "The deadline has expired");
        }
        if (!smsEntity.getMessage().equals(dto.getMessage())) {
            return new ApiResponseDTO(false, "The entered code is incorrect");
        }
        ProfileEntity entity = profileService.getByPhone(dto.getPhone());
        if (entity==null) throw new AppBadRequestException("The profile information is incorrect");
        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadRequestException("Wrong status");
        }
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity); // update
        return new ApiResponseDTO(true, "Registration completed");
    }

    public ApiResponseDTO login(AuthDTO authDTO) {

        ProfileEntity entity = profileService.getByPhone(authDTO.getPhone());
        if (entity == null || !entity.getPassword().equals(MD5Util.encode(authDTO.getPassword()))) {
            return new ApiResponseDTO(false, "Login or Password not found");
        }
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE) || !entity.getVisible()) {
            return new ApiResponseDTO(false, "Your status not active. Please contact with support.");
        }
        ProfileDTO response = new ProfileDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setSurname(entity.getSurname());
        response.setRole(entity.getRole());
        response.setPhone(entity.getPhone());
        response.setJwt(JWTUtil.encode(entity.getPhone(), entity.getRole())); // set jwt token
        // response
        return new ApiResponseDTO(true, response);
    }


}
