package com.example.houserental.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OtpService {

    private Map<String, String> otpStorage = new HashMap<>();
    private Map<String, Long> otpTime = new HashMap<>();

    public String generateOtp(String email) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        otpStorage.put(email, otp);
        otpTime.put(email, System.currentTimeMillis());
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        if (!otpStorage.containsKey(email)) return false;

        long generatedTime = otpTime.get(email);
        long currentTime = System.currentTimeMillis();

        // Expire after 5 minutes
        if ((currentTime - generatedTime) > 300000) {
            otpStorage.remove(email);
            otpTime.remove(email);
            return false;
        }

        return otp.equals(otpStorage.get(email));
    }

    public void clearOtp(String email) {
        otpStorage.remove(email);
        otpTime.remove(email);
    }
}