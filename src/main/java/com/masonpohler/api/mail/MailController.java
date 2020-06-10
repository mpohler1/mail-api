package com.masonpohler.api.mail;

import com.masonpohler.api.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
class MailController {
    private static final String DESTINATION_EMAIL_ADDRESS = System.getenv("DESTINATION_EMAIL_ADDRESS");
    private static final String AUTOMATED_MESSAGE_BODY = System.getenv("AUTOMATED_MESSAGE_BODY");

    private MailService service;

    @Autowired
    MailController(MailService service) {
        this.service = service;
    }

    @CrossOrigin("*")
    @PostMapping("/mail/send")
    @ResponseStatus(value = HttpStatus.OK)
    void sendMail(@RequestBody Mail mail) {
        try {
            String destinationMessageBody = "The following message is from " + mail.getFrom() + "\n\n" + mail.getBody();
            service.sendMail(DESTINATION_EMAIL_ADDRESS, mail.getSubject(), destinationMessageBody);
            service.sendMail(mail.getFrom(), "Message Received", AUTOMATED_MESSAGE_BODY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
