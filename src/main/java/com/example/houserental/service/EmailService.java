package com.example.houserental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String email, String otp) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("OTP Verification");
        msg.setText("Dear User,\r\n" + //
                        "\r\n" + //
                        "Thank you for choosing House Rental Platform. To complete your verification process, please use the One-Time Password (OTP) provided below. This OTP is valid for a limited time and should not be shared with anyone for security reasons.\r\n" + //
                        "\r\n" + //
                        "If you did not request this verification, please ignore this email.\r\n" + //
                        "\r\n" + //
                        "Your OTP is: " + otp +
                        "\n\n\nRegards,  \r\n" + //
                        "House Rental Platform Team");

        mailSender.send(msg);
    }
}