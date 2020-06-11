package com.masonpohler.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

class JavaMailServiceTest {

    @Mock
    private JavaMailSender mockedMailSender;

    @InjectMocks
    private JavaMailService service;

    @BeforeEach
    void set_up() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void send_mail_throws_mail_service_send_exception_when_mail_authentication_exception_is_thrown_in_java_mail_sender() {
        doThrow(MailAuthenticationException.class)
                .when(mockedMailSender).send(any(SimpleMailMessage.class));

        assertThrows(MailServiceSendException.class, () -> service.sendMail("", "", ""));
    }

    @Test
    void send_mail_throws_mail_service_parse_exception_when_mail_parse_exception_is_thrown_in_java_mail_sender() {
        doThrow(MailParseException.class)
                .when(mockedMailSender).send(any(SimpleMailMessage.class));

        assertThrows(MailServiceParseException.class, () -> service.sendMail("", "", ""));
    }

    @Test
    void send_mail_throws_mail_service_send_exception_when_mail_preparation_exception_is_thrown_in_java_mail_sender() {
        doThrow(MailPreparationException.class)
                .when(mockedMailSender).send(any(SimpleMailMessage.class));

        assertThrows(MailServiceSendException.class, () -> service.sendMail("", "", ""));
    }

    @Test
    void send_mail_throws_mail_service_send_exception_when_mail_send_exception_is_thrown_in_java_mail_sender() {
        doThrow(MailSendException.class)
                .when(mockedMailSender).send(any(SimpleMailMessage.class));

        assertThrows(MailServiceSendException.class, () -> service.sendMail("", "", ""));
    }

    @Test
    void send_mail_does_not_throw_exception_when_java_mail_sender_does_not_throw_exception() {
        assertDoesNotThrow(() -> service.sendMail("", "", ""));
    }
}
