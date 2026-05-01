package com.example.houserental.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.http.*;
import java.net.URI;

@Service
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String apiKey;

    @Value("${MAIL_FROM}")
    private String fromEmail;

    public void sendOtp(String toEmail, String otp) {
        try {
            String body = """
                {
                    "sender": {"email": "%s"},
                    "to": [{"email": "%s"}],
                    "subject": "OTP Verification",
                    "textContent": "Dear User,\\n\\nThank you for choosing House Rental Platform. To complete your verification process, please use the One-Time Password (OTP) provided below. This OTP is valid for a limited time and should not be shared with anyone for security reasons.\\n\\nIf you did not request this verification, please ignore this email.
\\n\\nYour OTP is: %s\\n\\nRegards,\\nHouse Rental Platform Team"
                }
                """.formatted(fromEmail, toEmail, otp);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                .header("accept", "application/json")
                .header("api-key", apiKey)
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

            HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 201) {
                throw new RuntimeException("Brevo API error: " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}