package com.masonpohler.api.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MailController {
    private static final String DESTINATION_EMAIL_ADDRESS = System.getenv("DESTINATION_EMAIL_ADDRESS");

    @Autowired
    private JavaMailSender mailSender;

    @CrossOrigin("*")
    @PostMapping("/mail/send")
    void sendMail(@RequestBody Mail mail) {
        SimpleMailMessage messageToDestination = new SimpleMailMessage();
        messageToDestination.setTo(DESTINATION_EMAIL_ADDRESS);
        messageToDestination.setSubject(mail.getSubject());
        messageToDestination.setText("The message below is from: " + mail.getFrom() + ":\n" + mail.getBody());
        mailSender.send(messageToDestination);

        SimpleMailMessage messageToUser = new SimpleMailMessage();
        messageToUser.setTo(mail.getFrom());
        messageToUser.setSubject("Message Received");
        messageToUser.setText("");
        mailSender.send(messageToUser);
    }
}
