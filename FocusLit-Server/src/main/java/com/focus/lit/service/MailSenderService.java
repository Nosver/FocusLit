package com.focus.lit.service;

import com.focus.lit.model.Mail;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface MailSenderService {
    public void sendNewMail(Mail mail) throws MessagingException;
}
