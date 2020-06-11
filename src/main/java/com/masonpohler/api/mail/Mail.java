package com.masonpohler.api.mail;

import lombok.Data;

@Data
class Mail {
    private String from;
    private String subject;
    private String body;
}
