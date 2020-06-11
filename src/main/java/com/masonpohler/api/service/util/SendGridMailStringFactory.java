package com.masonpohler.api.service.util;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendGridMailStringFactory {
    private static final Email FROM_EMAIL = new Email(System.getenv("MAIL_USERNAME"));

    public SendGridMailStringFactory() {
        // Instantiation used so this class can be mocked in testing
    }

    public String createMailString(String to, String subject, String body) throws IOException {
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(FROM_EMAIL, subject, toEmail, content);
        return mail.build();
    }
}
