package com.codewithjay.scm0_2.Service.impl;

import com.codewithjay.scm0_2.Service.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServicesImpl implements EmailServices {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("jayshiroya8@gmail.com");


        System.out.println(message);
        mailSender.send(message);
        System.out.println("mail send **************");

    }
}
