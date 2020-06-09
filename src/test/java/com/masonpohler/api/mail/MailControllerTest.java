package com.masonpohler.api.mail;

import com.masonpohler.api.mail.Mail;
import com.masonpohler.api.mail.MailController;
import org.junit.jupiter.api.Test;

class MailControllerTest {

    @Test
    void mail_endpoint_sends_mail() {
        Mail mail = new Mail();
        mail.setFrom("masonpohler@gmail.com");
        mail.setSubject("This is a test.");
        mail.setBody("This is a test message sent by Mason Pohler's mailbot.");
        MailController.sendMail(mail);
    }
}
