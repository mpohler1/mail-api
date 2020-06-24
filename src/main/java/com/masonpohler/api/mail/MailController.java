package com.masonpohler.api.mail;

import com.masonpohler.api.service.MailService;
import com.masonpohler.api.service.MailServiceParseException;
import com.masonpohler.api.service.MailServiceSendException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class MailController {
    // Environment variables allow these values to be customizable by different hosts of this api.
    // These are easy to configure when using docker-compose for deployment. Just add a .env file.
    // replace("\\n", "\n") is used to convert any newlines that may be included in env variables.
    static final String SUCCESS_RESPONSE_TEXT = System.getenv("SUCCESS_RESPONSE_TEXT").replace("\\n", "\n");
    static final String PARSE_FAILURE_RESPONSE_TEXT = System.getenv("PARSE_FAILURE_RESPONSE_TEXT").replace("\\n", "\n");
    static final String SEND_FAILURE_RESPONSE_TEXT = System.getenv("SEND_FAILURE_RESPONSE_TEXT").replace("\\n", "\n");

    private static final String DESTINATION_EMAIL_ADDRESS = System.getenv("DESTINATION_EMAIL_ADDRESS");
    private static final String DESTINATION_MESSAGE_HEADER = "The following message is from ";
    private static final String DESTINATION_MESSAGE_SUBJECT = "Message From ";

    @Autowired
    @Qualifier("sendGridMailService")
    private MailService service;

    @CrossOrigin("*")
    @PostMapping("/mail/send")
    ResponseEntity sendMail(@RequestBody MailModel mailModel) {
        try {
            String destinationMessageBody = DESTINATION_MESSAGE_HEADER + mailModel.getName() + " at " + mailModel.getEmail() + "\n\n" + mailModel.getBody();
            service.sendMail(DESTINATION_EMAIL_ADDRESS, DESTINATION_MESSAGE_SUBJECT + mailModel.getName(), destinationMessageBody);
            return new ResponseEntity<>(SUCCESS_RESPONSE_TEXT, HttpStatus.OK);

        } catch (MailServiceParseException e) {
            return new ResponseEntity<>(PARSE_FAILURE_RESPONSE_TEXT, HttpStatus.BAD_REQUEST);

        } catch (MailServiceSendException e) {
            return new ResponseEntity<>(SEND_FAILURE_RESPONSE_TEXT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
