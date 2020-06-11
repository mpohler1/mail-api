package com.masonpohler.api.mail;

import com.masonpohler.api.service.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.MailSendException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;


class MailModelControllerTest {

    @Mock
    private MailService mockedMailService;

    @InjectMocks
    private MailController controller;

    @BeforeEach
    void set_up() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void send_mail_returns_internal_server_error_when_mail_auth_exception_is_caught() {
        doThrow(MailAuthenticationException.class)
                .when(mockedMailService).sendMail(
                        any(String.class),
                        any(String.class),
                        any(String.class)
                );

        MailModel dummyMail = makeDummyMailModel();

        ResponseEntity expectedResponse = new ResponseEntity<>(MailController.AUTH_FAILURE_RESPONSE_TEXT, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity actualResponse = controller.sendMail(dummyMail);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void send_mail_returns_bad_request_when_mail_parse_exception_is_caught() {
        doThrow(MailParseException.class)
                .when(mockedMailService).sendMail(
                        any(String.class),
                        any(String.class),
                        any(String.class)
        );

        MailModel dummyMail = makeDummyMailModel();

        ResponseEntity expectedResponse = new ResponseEntity<>(MailController.PARSE_FAILURE_RESPONSE_TEXT, HttpStatus.BAD_REQUEST);
        ResponseEntity actualResponse = controller.sendMail(dummyMail);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void send_mail_returns_internal_server_error_when_mail_preparation_exception_is_caught() {
        doThrow(MailPreparationException.class)
                .when(mockedMailService).sendMail(
                any(String.class),
                any(String.class),
                any(String.class)
        );

        MailModel dummyMail = makeDummyMailModel();

        ResponseEntity expectedResponse = new ResponseEntity<>(MailController.PREPARATION_FAILURE_RESPONSE_TEXT, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity actualResponse = controller.sendMail(dummyMail);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void send_mail_returns_internal_server_error_when_mail_send_exception_is_caught() {
        doThrow(MailSendException.class)
                .when(mockedMailService).sendMail(
                any(String.class),
                any(String.class),
                any(String.class)
        );

        MailModel dummyMail = makeDummyMailModel();

        ResponseEntity expectedResponse = new ResponseEntity<>(MailController.SEND_FAILURE_RESPONSE_TEXT, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity actualResponse = controller.sendMail(dummyMail);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void send_mail_returns_ok_when_no_exception_is_thrown() {
        MailModel dummyMail = makeDummyMailModel();
        ResponseEntity expectedResponse = new ResponseEntity<>(MailController.SUCCESS_RESPONSE_TEXT, HttpStatus.OK);
        ResponseEntity actualResponse = controller.sendMail(dummyMail);
        assertEquals(expectedResponse, actualResponse);
    }

    private MailModel makeDummyMailModel() {
        MailModel dummyMailModel = new MailModel();
        dummyMailModel.setFrom("nobody@example.com");
        dummyMailModel.setSubject("Test");
        dummyMailModel.setBody("This is mail that will be used for unit testing.");
        return dummyMailModel;
    }
}
