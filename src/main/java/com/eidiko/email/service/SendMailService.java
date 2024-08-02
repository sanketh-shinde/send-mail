package com.eidiko.email.service;

public interface SendMailService {

    void sendMail(String to, String subject, String body);

    void sendMail(String from, String to, String subject, String body);

    void sendMailWithHtml(String to, String subject, String body, String username, String otp);
}
