package com.masonpohler.api.mail;

import lombok.Data;

@Data
class MailModel {
    private String name;
    private String email;
    private String body;
}
