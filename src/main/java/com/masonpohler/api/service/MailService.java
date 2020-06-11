package com.masonpohler.api.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendMail(String to, String subject, String body) throws MailServiceException;
}
