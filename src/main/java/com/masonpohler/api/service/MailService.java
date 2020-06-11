package com.masonpohler.api.service;

public interface MailService {
    void sendMail(String to, String subject, String body) throws MailServiceException;
}
