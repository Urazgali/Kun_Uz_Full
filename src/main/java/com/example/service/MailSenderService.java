package com.example.service;

import com.example.entity.ProfileEntity;
import com.example.thread.SendEmailThread;
import com.example.util.JWTUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Autowired
    private EmailHistoryService emailHistoryService;

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${server.url}")
    private String serverUrl;

    void sendEmail(String toAccount, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(text);
        javaMailSender.send(msg);
    }

    private void sendMimeEmail(String toAccount, String text) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject("Kun uz registration compilation");
            helper.setText(text, true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmailVerification(ProfileEntity entity) {
        String jwt = JWTUtil.encodeEmailJwt(entity.getId());
        String url = serverUrl + "/api/v1/auth/verification/email/" + jwt;

        String builder = String.format("<h1 style=\"text-align: center\">Hello %s</h1>", entity.getName()) +
                "<p>" +
                String.format("<a href=\"%s\"> Click link to complete registration </a>", url) +
                "</p>";
        sendMimeEmail(entity.getEmail(), builder);
        emailHistoryService.save(entity.getEmail(), builder); // save history
        SendEmailThread sendEmailThread = new SendEmailThread(entity.getEmail(), builder, javaMailSender);
        sendEmailThread.start(); // send
    }

}
