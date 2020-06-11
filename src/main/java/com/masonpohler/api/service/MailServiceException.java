package com.masonpohler.api.service;

import org.springframework.core.NestedRuntimeException;

abstract class MailServiceException extends NestedRuntimeException {

    MailServiceException(String msg) {
        super(msg);
    }
}
