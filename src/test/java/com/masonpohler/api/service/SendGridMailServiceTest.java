package com.masonpohler.api.service;

import com.masonpohler.api.service.util.SendGridMailStringFactory;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

class SendGridMailServiceTest {

    @Mock
    private SendGrid mockedSendGrid;

    @Mock
    private SendGridMailStringFactory mockedMailStringFactory;

    @InjectMocks
    private SendGridMailService service;

    @BeforeEach
    void set_up() {
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    void send_mail_throws_mail_service_parse_exception_when_create_mail_string_throws_io_exception() {
        doThrow(IOException.class)
                .when(mockedMailStringFactory).createMailString(
                        any(String.class),
                        any(String.class),
                        any(String.class)
        );

        assertThrows(MailServiceParseException.class, () -> service.sendMail("", "", ""));
    }

    @SneakyThrows
    @Test
    void send_mail_throws_mail_service_send_exception_when_send_request_throws_io_exception() {
        doThrow(IOException.class)
                .when(mockedSendGrid).api(any(Request.class));

        assertThrows(MailServiceSendException.class, () -> service.sendMail("", "", ""));
    }

    @Test
    void send_mail_does_not_throw_exception_when_mail_string_factory_and_send_grid_do_not_throw_exceptions() {
        assertDoesNotThrow(() -> service.sendMail("", "", ""));
    }
}
