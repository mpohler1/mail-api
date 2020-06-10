package com.masonpohler.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private JavaMailSender mailSender;

    @Autowired
    MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String subject, String body) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
