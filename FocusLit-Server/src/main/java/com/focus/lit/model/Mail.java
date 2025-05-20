package com.focus.lit.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mail {
    private String receiverEmail;
    private String subject;
    private String body;
}
