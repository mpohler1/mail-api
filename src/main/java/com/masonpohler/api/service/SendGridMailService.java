package com.masonpohler.api.service;

import com.masonpohler.api.service.util.SendGridMailStringFactory;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("sendGridMailService")
public class SendGridMailService implements MailService {

    private static final String MAIL_ENDPOINT = "mail/send";

    @Autowired
    private SendGrid sendGrid;

    @Autowired
    private SendGridMailStringFactory mailStringFactory;

    @Override
    public void sendMail(String to, String subject, String body) throws MailServiceException {
        String mailString = createMailString(to, subject, body);
        Request request = createRequest(mailString);
        sendRequest(request);
    }

    private String createMailString(String to, String subject, String body) throws MailServiceParseException {
        try {
            return mailStringFactory.createMailString(to, subject, body);
        } catch (IOException e) {
            throw new MailServiceParseException(e.getMessage());
        }
    }

    private Request createRequest(String mailString) {
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint(MAIL_ENDPOINT);
        request.setBody(mailString);
        return request;
    }

    private void sendRequest(Request request) throws MailServiceSendException {
        try {
            sendGrid.api(request);
        } catch (IOException e) {
            throw new MailServiceSendException(e.getMessage());
        }
    }
}
