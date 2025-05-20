package com.focus.lit.service.impl;


import com.focus.lit.model.Mail;
import com.focus.lit.service.MailSenderService;
import com.focus.lit.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderServiceImpl implements MailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    public void sendNewMail(Mail mail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart message

        helper.setTo(mail.getReceiverEmail());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBody(), true); // true indicates HTML content

        mailSender.send(message);
    }

}
