package com.example.thread;

import com.example.util.RandomUtil;
import okhttp3.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SendPhoneThread extends Thread {
    private String loginUrl = "http://localhost:8081/api/v1/auth/";
    private String sendSmsUrl = "http://localhost:8081/api/v1/sms";
    private String token;
    private LocalDateTime tokenLifeTime;
    RestTemplate restTemplate = new RestTemplate();
    private String phone;
    private String message;

    public SendPhoneThread(String phone, String message) {
        this.phone = phone;
        this.message = message;
    }

    @Override
    public void run() {
        if (token == null || tokenLifeTime.isAfter(LocalDateTime.now())) loginJwt();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);   // create header
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        String requestJson = "{\"phone\":\"" + phone + "\",\"message\":\"" + message + "\"}";  // create body
        HttpEntity<String> request = new HttpEntity<String>(requestJson, headers);   // create request
        restTemplate.postForEntity(sendSmsUrl, request, Boolean.class);
    }
    public void loginJwt() {
        Map<String, String> body = new HashMap<>();
        body.put("login", "ali123");
        body.put("password", "123456");
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body);
        ResponseEntity<String> response
                = restTemplate.postForEntity(loginUrl, request, String.class);
        token = response.getBody();
        tokenLifeTime = LocalDateTime.now().plusDays(7);
    }



    public void okHttp(String phone) {
        if (token == null || tokenLifeTime.isAfter(LocalDateTime.now())) loginJwt();
        try {
            String resourceUrl = "http://localhost:8081/api/v1/sms";
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"phone\":\"" + phone + "\",\"message\":\"" + RandomUtil.get() + "\"}");
            Request request = new Request.Builder()
                    .url(resourceUrl)
                    .method("POST", body).
                    addHeader("Authorization", "Bearer " + token)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            response.body();
            String jwt = response.message();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
