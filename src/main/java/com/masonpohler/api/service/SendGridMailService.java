package com.masonpohler.api.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SendGridMailService implements MailService {

    private static final String API_KEY = System.getenv("SEND_GRID_API_KEY");
    private static final Email FROM_EMAIL = new Email(System.getenv("MAIL_USERNAME"));

    private static final String MAIL_ENDPOINT = "mail/send";

    @Override
    public void sendMail(String to, String subject, String body) throws MailServiceException {
        Mail mail = buildMail(to, subject, body);
        Request request = buildRequest(mail);
        sendRequest(request);
    }

    private Mail buildMail(String to, String subject, String body) {
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", body);
        return new Mail(FROM_EMAIL, subject, toEmail, content);
    }

    private Request buildRequest(Mail mail) throws MailServiceParseException {
        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint(MAIL_ENDPOINT);
            request.setBody(mail.build());
            return request;
        } catch (IOException e) {
            throw new MailServiceParseException(e.getMessage());
        }
    }

    private void sendRequest(Request request) throws MailServiceSendException {
        try {
            SendGrid sendGrid = new SendGrid(API_KEY);
            sendGrid.api(request);
        } catch (IOException e) {
            throw new MailServiceSendException(e.getMessage());
        }
    }
}
