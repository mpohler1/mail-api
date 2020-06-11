package com.masonpohler.api.service;

import org.springframework.core.NestedRuntimeException;

public abstract class MailServiceException extends NestedRuntimeException {

    public MailServiceException(String msg) {
        super(msg);
    }
}
