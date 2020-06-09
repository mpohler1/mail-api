package com.masonpohler.api.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
class MailController {
    private static final String DESTINATION_EMAIL_ADDRESS = System.getenv("DESTINATION_EMAIL_ADDRESS");

    private JavaMailSender mailSender;

    @Autowired
    MailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @CrossOrigin("*")
    @PostMapping("/mail/send")
    @ResponseStatus(value = HttpStatus.OK)
    void sendMail(@RequestBody Mail mail) {
        SimpleMailMessage messageToDestination = new SimpleMailMessage();
        messageToDestination.setTo(DESTINATION_EMAIL_ADDRESS);
        messageToDestination.setSubject(mail.getSubject());
        messageToDestination.setText("The message below is from " + mail.getFrom() + "\n" + mail.getBody());
        mailSender.send(messageToDestination);

        SimpleMailMessage messageToUser = new SimpleMailMessage();
        messageToUser.setTo(mail.getFrom());
        messageToUser.setSubject("Message Received");
        messageToUser.setText("");
        mailSender.send(messageToUser);
    }
}
