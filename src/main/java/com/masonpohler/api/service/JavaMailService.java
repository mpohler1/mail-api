package com.masonpohler.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("javaMailService")
public class JavaMailService implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(String to, String subject, String body) throws MailServiceException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);

        } catch (MailParseException e) {
            throw new MailServiceParseException(e.getMessage());

        } catch (MailAuthenticationException | MailPreparationException | MailSendException e) {
            throw new MailServiceSendException(e.getMessage());
        }
    }
}
