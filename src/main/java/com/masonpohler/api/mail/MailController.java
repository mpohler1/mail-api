package com.masonpohler.api.mail;

import com.masonpohler.api.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.*;

@RestController
class MailController {
    // Environment variables allow these values to be customizable by different hosts of this api.
    // These are easy to configure when using docker-compose for deployment. Just add a .env file.
    // replace("\\n", "\n") is used to convert any newlines that may be included in env variables.
    static final String SUCCESS_RESPONSE_TEXT = System.getenv("SUCCESS_RESPONSE_TEXT").replace("\\n", "\n");
    static final String AUTH_FAILURE_RESPONSE_TEXT = System.getenv("AUTH_FAILURE_RESPONSE_TEXT").replace("\\n", "\n");
    static final String PARSE_FAILURE_RESPONSE_TEXT = System.getenv("PARSE_FAILURE_RESPONSE_TEXT").replace("\\n", "\n");
    static final String PREPARATION_FAILURE_RESPONSE_TEXT = System.getenv("PREPARATION_FAILURE_RESPONSE_TEXT").replace("\\n", "\n");
    static final String SEND_FAILURE_RESPONSE_TEXT = System.getenv("SEND_FAILURE_RESPONSE_TEXT").replace("\\n", "\n");

    private static final String DESTINATION_EMAIL_ADDRESS = System.getenv("DESTINATION_EMAIL_ADDRESS");
    private static final String AUTOMATED_MESSAGE_BODY = System.getenv("AUTOMATED_MESSAGE_BODY").replace("\\n", "\n");

    private static final String DESTINATION_MESSAGE_HEADER = "The following message is from ";
    private static final String AUTOMATED_MESSAGE_SUBJECT = "Message Received";

    private MailService service;

    @Autowired
    MailController(MailService service) {
        this.service = service;
    }

    @CrossOrigin("https://www.masonpohler.com")
    @PostMapping("/mail/send")
    ResponseEntity sendMail(@RequestBody Mail mail) {
        try {
            String destinationMessageBody = DESTINATION_MESSAGE_HEADER + mail.getFrom() + "\n\n" + mail.getBody();
            service.sendMail(DESTINATION_EMAIL_ADDRESS, mail.getSubject(), destinationMessageBody);
            service.sendMail(mail.getFrom(), AUTOMATED_MESSAGE_SUBJECT, AUTOMATED_MESSAGE_BODY);
            return new ResponseEntity<>(SUCCESS_RESPONSE_TEXT, HttpStatus.OK);

        } catch (MailAuthenticationException e) {
            return new ResponseEntity<>(AUTH_FAILURE_RESPONSE_TEXT, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (MailParseException e) {
            return new ResponseEntity<>(PARSE_FAILURE_RESPONSE_TEXT, HttpStatus.BAD_REQUEST);

        } catch (MailPreparationException e) {
            return new ResponseEntity<>(PREPARATION_FAILURE_RESPONSE_TEXT, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (MailSendException e) {
            return new ResponseEntity<>(SEND_FAILURE_RESPONSE_TEXT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
