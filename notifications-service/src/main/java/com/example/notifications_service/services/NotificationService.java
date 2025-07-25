package com.example.notifications_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendNotification(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setSubject(subject);
            message.setTo(to);
            message.setText(text);
            mailSender.send(message);
            log.info("Sent notification to - {}, message - {}", to, text);
        } catch (Exception e) {
            log.error("Error send notification to - {}, error - {}", to, e.getMessage(), e);
        }
    }

}
