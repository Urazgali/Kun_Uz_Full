package com.example.thread;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendEmailThread extends Thread {
    private JavaMailSender javaMailSender;
    private String toAccount;
    private String text;


    public SendEmailThread(String toAccount, String text, JavaMailSender javaMailSender) {
        this.toAccount = toAccount;
        this.text = text;
        this.javaMailSender = javaMailSender;
    }
    @Override
    public void run() {
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
}
